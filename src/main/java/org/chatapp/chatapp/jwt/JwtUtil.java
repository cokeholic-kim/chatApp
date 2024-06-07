package org.chatapp.chatapp.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private SecretKey secretKey;
    private String EMAIL = "email";
    private String ROLE = "role";

    public JwtUtil(@Value("${spring.jwt.secret}")String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(EMAIL, String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get(ROLE, String.class);
    }

    public Boolean isExpired(String token) {
        JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
        Jws<Claims> claimsJws = parser.parseSignedClaims(token);
        Claims payload = claimsJws.getPayload();
        Date expiration = payload.getExpiration();
        boolean before = expiration.before(new Date());
        return before;
    }

    public String createJwt(String username, String role, Long expiredMs) {
        long now = System.currentTimeMillis();
        Date expiration = new Date(now + expiredMs);
        return Jwts.builder()
                .claim(EMAIL, username)
                .claim(ROLE, role)
                .issuedAt(new Date(now))
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }
}
