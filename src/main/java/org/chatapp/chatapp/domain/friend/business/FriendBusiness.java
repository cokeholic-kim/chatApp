package org.chatapp.chatapp.domain.friend.business;

import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.domain.friend.converter.FriendConverter;
import org.chatapp.chatapp.domain.friend.service.FriendService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendBusiness {

    private final FriendConverter friendConverter;
    private final FriendService friendService;


    public void addFriend(String currentUser, String email) {
        //친구신청을 요청하는 유저 ,받는 유저의 이메일을 service로 전달
        friendService.addFriend(friendConverter.toEntity(currentUser, email));
    }

    public void deleteFriend(String deleteEmail, String requestEmail) {
        //친구 삭제를 요청하는 유저, 삭제되는 유저의 이메일을 service로 전달.
        friendService.deleteFriend(friendConverter.toEntity(requestEmail,deleteEmail));
    }
}
