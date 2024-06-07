package org.chatapp.chatapp.domain.security.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.chatapp.chatapp.db.user.UserEntity;
import org.chatapp.chatapp.db.user.UserRepository;
import org.chatapp.chatapp.domain.security.controller.dto.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity byEmail = userRepository.findByEmail(username).orElseThrow(IllegalArgumentException::new);
        String encodePassword = passwordEncoder.encode(byEmail.getPassword());

        return new CustomUserDetails(byEmail);
    }


}
