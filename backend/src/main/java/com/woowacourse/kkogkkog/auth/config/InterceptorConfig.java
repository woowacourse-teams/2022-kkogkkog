package com.woowacourse.kkogkkog.auth.config;

import com.woowacourse.kkogkkog.auth.support.JwtTokenProvider;
import com.woowacourse.kkogkkog.auth.presentation.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    public InterceptorConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(jwtTokenProvider))
            .addPathPatterns("/api/v2/members/me")
            .addPathPatterns("/api/v2/members/me/*")
            .addPathPatterns("/api/v2/coupons")
            .addPathPatterns("/api/v2/coupons/*")
            .addPathPatterns("/api/v2/coupons/*/event")
            .addPathPatterns("/api/v2/coupons/*/event/*")
            .addPathPatterns("/api/v2/coupons/*/status")
            .addPathPatterns("/api/v2/lazy-coupons")
            .addPathPatterns("/api/v2/lazy-coupons/*")
            .excludePathPatterns("/api/v2/lazy-coupons/code");
    }
}
