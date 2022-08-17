package com.woowacourse.kkogkkog.common.config;

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
            .addPathPatterns("/api/members/me")
            .addPathPatterns("/api/members/me/*")
            .addPathPatterns("/api/coupons")
            .addPathPatterns("/api/coupons/me")
            .addPathPatterns("/api/reservations")
            .addPathPatterns("/api/reservations/*")
            .addPathPatterns("/api/coupons/*/event")
            .addPathPatterns("/api/coupons/*/event/*");
    }
}