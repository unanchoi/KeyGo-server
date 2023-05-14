package maddori.keygo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import maddori.keygo.common.exception.CustomException;
import maddori.keygo.common.response.ResponseCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    private final String secretKey;

    public JwtAuthenticationInterceptor(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // request header에서 access_token 값 가져오기
        String accessToken = request.getHeader("access_token");
        String refreshToken = request.getHeader("refresh_token");

        // accessToken 유효성 검사
        if (accessToken != null) {
            try {
                // JWT 토큰 디코딩
                Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();

                // 인증 정보 저장
                JwtAuthenticationToken auth = new JwtAuthenticationToken(claims, accessToken, refreshToken);
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new CustomException(ResponseCode.NO_TOKEN);
        }
        return true;
    }
}
