package org.chatapp.chatapp.domain.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserRequest {
    @Email
    private String email;
    private String password;
    private String role;
}
