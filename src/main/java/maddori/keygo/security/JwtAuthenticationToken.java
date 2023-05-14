package maddori.keygo.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principle;
    private final String accessToken;
    private final String refreshToken;
    private final Long id;

    public JwtAuthenticationToken(Claims claims, String accessToken, String refreshToken) {
        super(null);
        this.principle = claims.getSubject();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = claims.get("id", Long.class);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principle;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getId() {
        return id;
    }
}
