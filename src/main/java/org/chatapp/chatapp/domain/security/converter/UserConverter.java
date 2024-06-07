package org.chatapp.chatapp.domain.security.converter;

import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.db.user.Enum.UserRole;
import org.chatapp.chatapp.db.user.UserEntity;
import org.chatapp.chatapp.domain.security.controller.dto.UserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConverter {

    private final PasswordEncoder passwordEncoder;

    public UserEntity toEntity(UserRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.valueOf(request.getRole()))
                .build();
    }
}
