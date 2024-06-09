package org.chatapp.chatapp.domain.user.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.common.api.Api;
import org.chatapp.chatapp.domain.security.business.UserBusiness;
import org.chatapp.chatapp.domain.user.dto.UserRequest;
import org.chatapp.chatapp.domain.user.dto.UserUpdateRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserBusiness userBusiness;

    @PostMapping("/join")
    public Api<String> join(@Valid UserRequest userRequest) {
        userBusiness.joinProcess(userRequest);
        return Api.OK("join success");
    }

    @PostMapping("/user/quit")
    public Api<String> userQuit(String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        UserRequest userRequest = UserRequest.builder()
                .email(name)
                .password(password)
                .role(role).build();
        userBusiness.userQuit(userRequest);
        return Api.OK("quit success");
    }

    @PostMapping("/user/update")
    public Api<String> update(UserUpdateRequest userUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        userUpdateRequest.setEmail(name);
        userUpdateRequest.setRole(role);
        userBusiness.update(userUpdateRequest);
        return Api.OK("update success");
    }

}
