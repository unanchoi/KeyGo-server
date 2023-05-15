package maddori.keygo.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityService {
    public static Long getCurrentUserId() {
        JwtAuthenticationToken auth = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return auth.getId();
    }
}
