package org.chatapp.chatapp.domain.friend.user;

import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.common.api.Api;
import org.chatapp.chatapp.domain.friend.business.FriendBusiness;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/friend")
@RequiredArgsConstructor
public class UserFriendController {

    private final FriendBusiness friendBusiness;

    //친구신청 (현재 시점 친구아이디를 통해서 바로 추가가능)
    //TODO 이후 친구기능이 수정된경우 신청을 보내고 수락해야 조회 가능하도록 수정.
    @PostMapping("/add")
    public Api<String> addFriend(String requestedEmail) {
        // 요청하는 유저의 정보를 토큰에서 받아서 넘겨준다.
        String requestEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        friendBusiness.addFriend(requestEmail, requestedEmail);

        return Api.OK("add friend success");
    }

    //친구삭제
    @PostMapping("/delete")
    public Api<String> deleteFriend(String deleteEmail){
        // 요청하는 유저의 정보를 토큰에서 받아서 넘겨준다.
        String requestEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        friendBusiness.deleteFriend(deleteEmail,requestEmail);

        return Api.OK("delete friend success");
    }

    //친구조회
}
