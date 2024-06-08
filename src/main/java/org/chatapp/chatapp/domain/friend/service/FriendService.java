package org.chatapp.chatapp.domain.friend.service;

import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.common.error.ErrorCode;
import org.chatapp.chatapp.common.exception.ApiException;
import org.chatapp.chatapp.db.friend.FriendEntity;
import org.chatapp.chatapp.db.friend.FriendRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;

    @Transactional
    public void addFriend(FriendEntity friendEntity) {

        //요청하는 친구가 이미 신청된 친구인지 조회,
        Boolean isExistFriend = friendRepository.existsByFriend(friendEntity.getFriend());
        if (isExistFriend) {
            throw new ApiException(ErrorCode.BAD_REQUEST,"이미 신청된 친구요청입니다.");
        }

        friendRepository.save(friendEntity);
    }

    public void deleteFriend(FriendEntity friendEntity) {
        Boolean isExistFriend = friendRepository.existsByFriend(friendEntity.getFriend());
        if (!isExistFriend) {
            throw new ApiException(ErrorCode.BAD_REQUEST,"이미 신청된 친구요청입니다.");
        }

        friendRepository.delete(friendEntity);
    }
}
