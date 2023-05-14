package maddori.keygo.config;

import maddori.keygo.security.JwtAuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final String secretKey;

    public WebMvcConfig(String secretKey) {
        this.secretKey = secretKey;
    }

    // 정적 리소스 핸들러 추가
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }

    // 유저 인증 위한 인터셉터 추가
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtAuthenticationInterceptor(secretKey))
                .addPathPatterns("/**");
    }
}



