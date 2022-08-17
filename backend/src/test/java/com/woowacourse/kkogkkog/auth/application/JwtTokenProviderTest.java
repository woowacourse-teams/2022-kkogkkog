package com.woowacourse.kkogkkog.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.auth.UnauthenticatedTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("JwtTokenProvider 클래스의")
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Nested
    @DisplayName("getValidatedPayload 메서드는")
    class GetValidatedPayload {

        @Test
        @DisplayName("유효한 토큰을 받으면, payload를 반환한다.")
        void success() {
            String token = jwtTokenProvider.createToken(Long.toString(1L));

            String payload = jwtTokenProvider.getValidatedPayload(token);

            assertThat(payload).isEqualTo(Long.toString(1L));
        }

        @Test
        @DisplayName("가짜 토큰을 받으면, 예외를 던진다.")
        void fail_fakeToken() {
            String fakeToken = "FakeToken";

            assertThatThrownBy(() -> jwtTokenProvider.getValidatedPayload(fakeToken))
                .isInstanceOf(UnauthenticatedTokenException.class);
        }

        @Test
        @DisplayName("만료기간이 지난 토큰을 받으면, 예외를 던진다.")
        void fail_expiredToken() {
            String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU3Nzg3Mzk3LCJleHAiOjE2NTc3ODc0MzN9.B1dAhi-5oyQSeZjAPYYNPA_eeYxF00HO6YPqq58bk1_Yr7tsV44LhY_n4RVpLW8StdbbLuui7uwgSBLu_uM2cQ";

            assertThatThrownBy(() -> jwtTokenProvider.getValidatedPayload(expiredToken))
                .isInstanceOf(UnauthenticatedTokenException.class);
        }
    }
}
