package com.woowacourse.kkogkkog.application;

import static com.woowacourse.kkogkkog.fixture.MemberFixture.ROOKIE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.exception.auth.ErrorResponseToGetAccessTokenException;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class AuthServiceTest extends ServiceTest {

    @Autowired
    private AuthService authService;


    @Test
    @DisplayName("로그인에 성공하면 토큰을 반환한다")
    void login() {
        MemberResponse memberResponse = MemberResponse.of(ROOKIE);
        given(slackRequester.getUserInfoByCode("code"))
            .willReturn(
                new SlackUserInfo(
                    memberResponse.getUserId(),
                    memberResponse.getWorkspaceId(),
                    memberResponse.getNickname(),
                    memberResponse.getImageUrl()));

        TokenResponse tokenResponse = authService.login("code");

        Assertions.assertThat(tokenResponse).isNotNull();
    }

    @Test
    @DisplayName("올바르지 않은 임시 코드를 입력하면, 예외를 던진다")
    void fail_invalidCode() {
        given(slackRequester.getUserInfoByCode("invalid_code"))
            .willThrow(new ErrorResponseToGetAccessTokenException("invalid_code"));

        assertThatThrownBy(
            () -> authService.login("invalid_code")
        ).isInstanceOf(ErrorResponseToGetAccessTokenException.class);
    }
}
