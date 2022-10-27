package com.woowacourse.kkogkkog.auth.presentation;

import static org.hibernate.validator.internal.metadata.core.ConstraintHelper.PAYLOAD;

import com.woowacourse.kkogkkog.auth.support.JwtTokenProvider;
import com.woowacourse.kkogkkog.auth.exception.UnauthenticatedTokenException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            return true;
        }

        String token = AuthorizationExtractor.extract(request);
        try {
            String payload = jwtTokenProvider.getValidatedPayload(token);
            request.setAttribute(PAYLOAD, payload);
            return true;
        } catch (UnauthenticatedTokenException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }
}
