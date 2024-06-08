package org.chatapp.chatapp.domain.friend.converter;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.common.error.ErrorCode;
import org.chatapp.chatapp.common.exception.ApiException;
import org.chatapp.chatapp.db.friend.Enum.FriendStatus;
import org.chatapp.chatapp.db.friend.FriendEntity;
import org.chatapp.chatapp.db.user.UserEntity;
import org.chatapp.chatapp.db.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendConverter {

    private final UserRepository userRepository;

    public FriendEntity toEntity(String requestEmail, String requestedEmail) {
        // 요청하는 유저와 요청되는 유저의 객체를 조회
        UserEntity requestUser = userRepository.findByEmail(requestEmail).orElseThrow(() -> new ApiException(
                ErrorCode.BAD_REQUEST));
        UserEntity requestedUser = userRepository.findByEmail(requestedEmail).orElseThrow(() -> new ApiException(
                ErrorCode.BAD_REQUEST));

        return FriendEntity.builder()
                .user(requestUser)
                .friend(requestedUser)
                .status(FriendStatus.ACCEPT)
                .createdAt(LocalDateTime.now())
                .build();

    }
}
