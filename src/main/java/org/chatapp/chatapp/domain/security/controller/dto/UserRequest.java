package org.chatapp.chatapp.domain.security.controller.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {
    @Email
    private String email;
    private String password;
    private String role;
}
