package org.chatapp.chatapp.domain.security.controller;

import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.domain.security.business.UserBusiness;
import org.chatapp.chatapp.domain.security.controller.dto.UserRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserBusiness userBusiness;

    @PostMapping("/join")
    public String join(UserRequest userRequest){
        userBusiness.joinProcess(userRequest);
        return "ok";
    }

    @PostMapping("/login")
    public void login(){

    }
}
