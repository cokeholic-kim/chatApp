package org.chatapp.chatapp.domain.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class UserUpdateRequest {
    @Email
    private String email;
    private String role;
    private String password;
    private String nickName;
    private MultipartFile profileImage;
}
