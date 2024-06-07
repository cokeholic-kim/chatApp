package org.chatapp.chatapp.domain.security.service;

import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.db.user.UserEntity;
import org.chatapp.chatapp.db.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Transactional
    public void joinProcess(UserEntity userEntity) {
        Boolean isExistEmail = userRepository.existsByEmail(userEntity.getEmail());
        if(isExistEmail){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }
        userRepository.save(userEntity);
    }
}
