package org.chatapp.chatapp.common.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chatapp.chatapp.common.error.ErrorCode;
import org.chatapp.chatapp.common.exception.ApiException;
import org.chatapp.chatapp.db.chatroom.ChatRoom;
import org.chatapp.chatapp.db.chatroom.ChatRoomRepository;
import org.chatapp.chatapp.db.chatroomusers.ChatRoomUsersEntity;
import org.chatapp.chatapp.db.chatroomusers.ChatRoomUsersRepository;
import org.chatapp.chatapp.db.offlinechathistory.OfflineChatHistoryEntity;
import org.chatapp.chatapp.db.offlinechathistory.OfflineChatHistoryRepository;
import org.chatapp.chatapp.db.user.UserEntity;
import org.chatapp.chatapp.db.user.UserRepository;
import org.chatapp.chatapp.jwt.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class SocketTextHandler extends TextWebSocketHandler {
    private final UserRepository userRepository;
    private final ChatRoomRepository roomRepository;
    private final ChatRoomUsersRepository chatRoomUsersRepository;
    private final OfflineChatHistoryRepository offlineChatHistoryRepository;
    private final JwtUtil jwtUtil;

    private final Map<String, WebSocketSession> userSessionMap = new ConcurrentHashMap<>();

    private final ObjectMapper mapper;

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 전송할 수신자에 대한 session과 정보.
        ChatMessageDto chatMessageDto = getMessageDto(message);
        //전송유저
        UserEntity senderUser = getUserEntity(session);

        // 1. 만약 페이로드에 roomId가 없다면 room을 생성
        if (chatMessageDto.getChatRoomId() == null) {
            // room 이 없는경우 수신자 이메일값이 필수.
            // 해당 이메일정보로 수신자를 조회
            List<UserEntity> receivers = chatMessageDto.getReceiverEmail().stream().map(receiver ->
                    userRepository.findByEmail(receiver)
                            .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, receiver + " 는 없는 유저입니다."))
            ).toList();

            // 2. 송신자와 수신자를 해당 방에 가입.
            List<UserEntity> allUser = new ArrayList<>(receivers);
            allUser.add(senderUser);

            ChatRoom room = new ChatRoom();

            List<ChatRoomUsersEntity> chatRoomUsersList = allUser.stream()
                    .map(user -> ChatRoomUsersEntity.builder()
                            .userId(user.getId())
                            .joinedAt(LocalDateTime.now())
                            .chatRoom(room)
                            .build())
                    .collect(Collectors.toList());

            room.setChatRoomUsers(chatRoomUsersList);
            room.setName(senderUser.getEmail() + "to" + chatMessageDto.getReceiverEmail());
            room.setCreatedAt(LocalDateTime.now());

            roomRepository.save(room);

            sendMessageAndkeep(receivers, chatMessageDto, senderUser);
        } else {
            // room 이 있는경우 .
            // room에 등록된 유저를 조회
            ChatRoom chatRoom = roomRepository.findById(chatMessageDto.getChatRoomId())
                    .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "없는 채팅방 입니다."));
            // 송신유저 외에 유저에게 메시지를 전송.
            List<ChatRoomUsersEntity> chatRoomUsers = chatRoom.getChatRoomUsers();

            List<UserEntity> rooUserList = chatRoomUsers.stream()
                    .filter(chatRoomUser -> !Objects.equals(chatRoomUser.getUserId(), senderUser.getId()))
                    .map(chatRoomUser -> {
                        return userRepository.findById(chatRoomUser.getUserId())
                                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "없는 유저입니다."));
                    }).toList();

            sendMessageAndkeep(rooUserList, chatMessageDto, senderUser);
        }

        // 수신자에게 메시지 전송

    }

    @Override
    @Transactional
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //유저의 이메일을 얻어온다.

        String userId = getUserId(session);
        UserEntity userEntity = userRepository.findByEmail(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "없는 유저입니다."));

        // 오프라인 유저 리스트에 해당 유저가 있는지 확인
        if (offlineChatHistoryRepository.existsByReceiverId(userEntity.getId())) {
            // chatRepository에서 해당 유저의 오프라인 메시지 정보 조회
            List<OfflineChatHistoryEntity> offlineMessages = offlineChatHistoryRepository.findByReceiverId(
                    userEntity.getId());

            // 조회한 메시지 정보를 새로 연결된 세션을 통해 전송
            for (OfflineChatHistoryEntity message : offlineMessages) {
                sendMessage(session, message.getContent());
            }

            // 오프라인 유저 리스트에서 해당 유저 제거
            offlineChatHistoryRepository.deleteAllByReceiverId(userEntity.getId());
        }

        //유저의 이메일과 세션을 매핑해서 서버에서 관리.
        userSessionMap.put(userId, session);
        log.info("{} 환영합니다", userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = getUserId(session);
        userSessionMap.remove(userId);
        log.info("클라이언트와 연결이 해제 되었습니다.");
    }


    private String getUserId(WebSocketSession session) {
        return
                jwtUtil.getUsername(
                        Objects.requireNonNull(session.getHandshakeHeaders().get("Authorization")).get(0)
                                .split(" ")[1]);
    }

    private UserEntity getUserEntity(WebSocketSession session) {
        return userRepository.findByEmail(getUserId(session))
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST));
    }

    private ChatMessageDto getMessageDto(TextMessage message) throws JsonProcessingException {
        return mapper.readValue(message.getPayload(), ChatMessageDto.class);
    }

    private void sendMessageAndkeep(List<UserEntity> userEntities, ChatMessageDto chatMessageDto, UserEntity sender) {
        //연결중인 세션만 찾아서 문자를전송
        getOnlineSession(userEntities).forEach(
                receiverSession -> sendMessage(receiverSession, chatMessageDto.getMessage()));

        //연결중이지않은 유저는 해당 기록을 저장.
        saveOfflineChat(userEntities, chatMessageDto, sender);
    }

    private void saveOfflineChat(List<UserEntity> userEntities, ChatMessageDto chatMessageDto, UserEntity sender) {
        List<UserEntity> offlineReceivers = userEntities.stream()
                .filter(receiver -> userSessionMap.get(receiver.getEmail()) == null)
                .toList();

        offlineReceivers.forEach(offlineUser -> {
            OfflineChatHistoryEntity offlineChat = OfflineChatHistoryEntity.builder()
                    .senderId(sender.getId())
                    .receiverId(offlineUser.getId())
                    .content(chatMessageDto.getMessage())
                    .createdAt(LocalDateTime.now())
                    .build();
            offlineChatHistoryRepository.save(offlineChat);
        });
    }

    private List<WebSocketSession> getOnlineSession(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(receiver -> userSessionMap.get(receiver.getEmail()))
                .filter(Objects::nonNull)
                .toList();
    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
