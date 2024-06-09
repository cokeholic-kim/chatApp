package org.chatapp.chatapp.domain.security.converter;

import static org.chatapp.chatapp.common.UploadService.createFileName;

import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.common.UploadService;
import org.chatapp.chatapp.db.user.Enum.UserRole;
import org.chatapp.chatapp.db.user.UserEntity;
import org.chatapp.chatapp.domain.user.dto.UserRequest;
import org.chatapp.chatapp.domain.user.dto.UserUpdateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConverter {



    public UserEntity toEntity(UserRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(UserRole.valueOf(request.getRole()))
                .build();
    }

    public UserEntity toEntityNotEncodePassword(UserRequest request){
        return UserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(UserRole.valueOf(request.getRole()))
                .build();
    }

    public UserEntity toEntity(UserUpdateRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .profileImage(createFileName(request.getProfileImage()))
                .nickName(request.getNickName())
                .role(UserRole.valueOf(request.getRole()))
                .build();
    }
}
