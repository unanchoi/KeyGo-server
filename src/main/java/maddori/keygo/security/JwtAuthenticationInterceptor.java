package maddori.keygo.security;

import org.springframework.web.servlet.HandlerInterceptor;

public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    public JwtAuthenticationInterceptor(String secretKey) {
    }
}
