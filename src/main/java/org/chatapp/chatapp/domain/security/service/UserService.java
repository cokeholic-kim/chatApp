package org.chatapp.chatapp.domain.security.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.common.UploadService;
import org.chatapp.chatapp.common.error.ErrorCode;
import org.chatapp.chatapp.common.exception.ApiException;
import org.chatapp.chatapp.db.user.UserEntity;
import org.chatapp.chatapp.db.user.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UploadService uploadService;

    //TODO 이메일로 객체를 조회하고 비밀번호로 가져오는 과정 validation 코드 필요, 메서드로 분리.

    @Transactional
    public void joinProcess(UserEntity userEntity) {
        Boolean isExistEmail = userRepository.existsByEmail(userEntity.getEmail());
        if (isExistEmail) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    public void userQuit(UserEntity entity) {

        UserEntity userEntity = userRepository.findByEmail(entity.getEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "요청한 유저의 정보가 없습니다."));
        boolean matches = passwordEncoder.matches(entity.getPassword(), userEntity.getPassword());
        if (!matches) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
        userRepository.delete(userEntity);
    }

    @Transactional
    public void userUpdate(UserEntity newUser, MultipartFile image) {
        //엔티티 정보로 db에서 일치하는 객체를 찾아온다.
        UserEntity oldUser = userRepository.findByEmail(newUser.getEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "요청한 유저의 정보가 없습니다."));
        //비밀번호가 일치하는지 확인
        boolean matches = passwordEncoder.matches(newUser.getPassword(), oldUser.getPassword());
        if (!matches) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
        oldUser.setPassword(passwordEncoder.encode(oldUser.getPassword()));
        //프로필 이미지를 저장
        //유저 객체에 이미지가 있는경우 이미지를 찾아서 지우고 저장, 없는경우 바로저장
        if (Objects.isNull(oldUser.getProfileImage()) || oldUser.getProfileImage().isBlank()) {
            uploadService.saveFile(image, newUser.getProfileImage());
        } else {
            uploadService.updateFile(oldUser.getProfileImage(), image, newUser.getProfileImage());
        }

        //객체를 DB에 업데이트
        BeanUtils.copyProperties(newUser, oldUser, "id");
    }
}
