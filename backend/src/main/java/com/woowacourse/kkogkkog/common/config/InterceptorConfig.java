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
            .addPathPatterns("/api/v2/members/me")
            .addPathPatterns("/api/v2/members/me/*")
            .addPathPatterns("/api/coupons")
            .addPathPatterns("/api/coupons/accept")
            .addPathPatterns("/api/coupons/sent")
            .addPathPatterns("/api/coupons/received")
            .addPathPatterns("/api/coupons/me")
            .addPathPatterns("/api/v2/coupons")
            .addPathPatterns("/api/v2/coupons/code")
            .addPathPatterns("/api/v2/coupons/*")
            .addPathPatterns("/api/v2/coupons/accept")
            .addPathPatterns("/api/v2/coupons/sent")
            .addPathPatterns("/api/v2/coupons/received")
            .addPathPatterns("/api/v2/coupons/me")
            .addPathPatterns("/api/reservations")
            .addPathPatterns("/api/reservations/*")
            .addPathPatterns("/api/v2/coupons/*/event")
            .addPathPatterns("/api/v2/coupons/*/event/*")
            .addPathPatterns("/api/v2/coupons/*/status")
            .addPathPatterns("/api/coupons/*/event")
            .addPathPatterns("/api/coupons/*/event/*")
            .addPathPatterns("/api/coupons/*/status")
            .addPathPatterns("/api/v2/coupons/unregistered")
            .addPathPatterns("/api/v2/coupons/unregistered/*")
            .excludePathPatterns("/api/v2/coupons/unregistered/code");
    }
}
