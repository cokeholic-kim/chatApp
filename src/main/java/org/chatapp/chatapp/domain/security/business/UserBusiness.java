package org.chatapp.chatapp.domain.security.business;

import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.db.user.UserEntity;
import org.chatapp.chatapp.domain.user.dto.UserRequest;
import org.chatapp.chatapp.domain.security.converter.UserConverter;
import org.chatapp.chatapp.domain.security.service.UserService;
import org.chatapp.chatapp.domain.user.dto.UserUpdateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBusiness {

    private final UserConverter userConverter;
    private final UserService userService;

    public void joinProcess(UserRequest request){
        UserEntity userEntity = userConverter.toEntity(request);

        userService.joinProcess(userEntity);
    }

    public void userQuit(UserRequest request) {
        UserEntity entity = userConverter.toEntityNotEncodePassword(request);

        userService.userQuit(entity);
    }

    public void update(UserUpdateRequest userUpdateRequest) {
        UserEntity entity = userConverter.toEntity(userUpdateRequest);
        userService.userUpdate(entity,userUpdateRequest.getProfileImage());
    }
}
