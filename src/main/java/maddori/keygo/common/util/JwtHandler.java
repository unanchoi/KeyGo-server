package maddori.keygo.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtHandler {
    private final Environment environment;
    private final String secretKey;
    private final Long accessExpiration = 1000L * 60 * 60 * 24 * 365;
    private final Long refreshExpiration = 1000L * 60 * 60 * 24 * 365 * 2;

    public JwtHandler(Environment environment) {
        this.environment = environment;
        this.secretKey = environment.getProperty("jwt.secret");
    }

    // accessToken 생성
    public String createAccessToken(Long userId) {
        Date now = new Date();

        return Jwts.builder()
                .claim("id", userId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // refreshToken 생성
    public String createRefreshToken() {
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
