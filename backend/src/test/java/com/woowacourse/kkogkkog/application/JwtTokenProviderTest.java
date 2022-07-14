package com.woowacourse.kkogkkog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.auth.UnauthenticatedTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("토큰을 생성하고, 토큰의 페이로드를 변환할 수 있다")
    void getValidatedPayload() {
        String token = jwtTokenProvider.createToken(Long.toString(1L));

        String payload = jwtTokenProvider.getValidatedPayload(token);

        assertThat(payload).isEqualTo(Long.toString(1L));
    }

    @Test
    @DisplayName("가짜 토큰을 받으면 예외를 던진다.")
    void validateFakeToken() {
        String fakeToken = "FakeToken";

        assertThatThrownBy(() -> jwtTokenProvider.getValidatedPayload(fakeToken))
                .isInstanceOf(UnauthenticatedTokenException.class);
    }

    @Test
    @DisplayName("만료기간이 지난 토큰을 받으면 예외를 던진다.")
    void validateExpirationDate() {
        String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwZXBwZXJAd29vd2Fjb3Vyc2UuY29tIiwiaWF0IjoxNjU0NDkzMDM0LCJleHAiOjE2NTQ0OTMxMzR9.CiCFn0f42ro8nhoH__Fs2wXQTPi5g8GVTN7Ae4sc5k_2RQNNAx0gcxtaDcxDZgLbqk7ploc7GJxZUfXTWnq3uQ";

        assertThatThrownBy(() -> jwtTokenProvider.getValidatedPayload(expiredToken))
                .isInstanceOf(UnauthenticatedTokenException.class);
    }
}
