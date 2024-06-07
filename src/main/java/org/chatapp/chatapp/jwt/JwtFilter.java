package org.chatapp.chatapp.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chatapp.chatapp.db.user.Enum.UserRole;
import org.chatapp.chatapp.db.user.UserEntity;
import org.chatapp.chatapp.domain.security.controller.dto.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        //토큰 유무검증
        if (authorization == null || !authorization.startsWith("Bearer")) {
            log.error("token null");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            log.error("token expired");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰이 있고 아직 유효한경우
        setAuthentication(token);

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token) {
        // 토큰에서 정보 추출
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        // 유저엔티티 생성
        UserEntity user = UserEntity.builder()
                .email(username)
                .password("")
                .role(UserRole.valueOf(role))
                .build();
        // 유저 엔티로 커스텀 유저디테일 생성후 인증토큰 생성
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities());
        //인증 토큰 세팅
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
