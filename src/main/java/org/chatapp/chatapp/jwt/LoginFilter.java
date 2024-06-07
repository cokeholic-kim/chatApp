package org.chatapp.chatapp.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chatapp.chatapp.domain.security.controller.dto.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String userName = obtainUsername(request);
        String password = obtainPassword(request);
        //post에서 email이라는 key로 보내는 경우
//        String email = obtainEmail(request);

        log.info("username {}",userName);
        log.info("password {}",password);
//        log.info("email {}",email);


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,password,null);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        //인증 성공
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String username = customUserDetails.getUsername();
        String role = getRole(customUserDetails);

        //jwt 발급
        String token = jwtUtil.createJwt(username,role,60*60*1000L);
        response.addHeader("Authorizaton","Bearer " + token);
    }

    private String getRole(CustomUserDetails customUserDetails) {
        return customUserDetails.getAuthorities().iterator().next().getAuthority();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        //인증 실패
        response.setStatus(401);
    }

    private String obtainEmail(HttpServletRequest request) {
        return request.getParameter("email");
    }
}
