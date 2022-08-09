package com.woowacourse.kkogkkog.application;

import static com.woowacourse.kkogkkog.fixture.MemberFixture.ROOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.exception.auth.AccessTokenRetrievalFailedException;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DisplayName("AuthService 클래스의")
class AuthServiceTest extends ServiceTest {

    private static final String AUTHORIZATION_CODE = "code";

    @Autowired
    private AuthService authService;

    @Nested
    @DisplayName("login 메서드는")
    class Login {

        @Test
        @DisplayName("임시 코드를 입력받으면, 토큰과 초기 사용자 여부를 반환한다.")
        void success() {
            MemberResponse memberResponse = MemberResponse.of(ROOKIE);
            given(slackClient.getUserInfoByCode(AUTHORIZATION_CODE))
                .willReturn(
                    new SlackUserInfo(
                        memberResponse.getUserId(),
                        memberResponse.getNickname(),
                        memberResponse.getEmail(),
                        memberResponse.getImageUrl(),
                        memberResponse.getWorkspaceId(),
                        "TEAM_ID",
                        "팀_이미지_주소"
                    ));

            TokenResponse tokenResponse = authService.login(AUTHORIZATION_CODE);

            assertThat(tokenResponse).isNotNull();
        }

        @Test
        @DisplayName("올바르지 않은 임시 코드를 입력하면, 예외를 던진다")
        void fail_invalidCode() {
            given(slackClient.getUserInfoByCode("invalid_code"))
                .willThrow(new AccessTokenRetrievalFailedException("invalid_code"));

            assertThatThrownBy(
                () -> authService.login("invalid_code")
            ).isInstanceOf(AccessTokenRetrievalFailedException.class);
        }
    }
}
