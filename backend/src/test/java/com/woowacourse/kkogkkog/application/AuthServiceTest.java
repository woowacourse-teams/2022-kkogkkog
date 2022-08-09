package com.woowacourse.kkogkkog.application;

import static com.woowacourse.kkogkkog.fixture.MemberFixture.ROOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.exception.auth.AccessTokenRetrievalFailedException;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.WorkspaceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DisplayName("AuthService 클래스의")
class AuthServiceTest extends ServiceTest {

    private static final String AUTHORIZATION_CODE = "code";
    private static final String WORKSPACE_ID = "workspace_id";
    private static final String WORKSPACE_NAME = "workspace_name";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

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
                        memberResponse.getWorkspaceId(),
                        memberResponse.getNickname(),
                        memberResponse.getEmail(),
                        memberResponse.getImageUrl(),
                        "workspace_name"));

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

    @Nested
    @DisplayName("installSlackApp 메서드는")
    class InstallSlackBot {

        @Test
        @DisplayName("임시 코드를 입력하면, 봇 토큰을 저장한다")
        void success() {
            given(slackClient.requestBotAccessToken(AUTHORIZATION_CODE))
                .willReturn(new WorkspaceResponse(WORKSPACE_ID, WORKSPACE_NAME, ACCESS_TOKEN));

            assertThatNoException()
                .isThrownBy(() -> authService.installSlackApp(AUTHORIZATION_CODE));
        }

        @Test
        @DisplayName("이미 등록된 워크스페이스인 경우, 엑세스 토큰을 업데이트한다.")
        void success_update() {
            given(slackClient.requestBotAccessToken(AUTHORIZATION_CODE))
                .willReturn(new WorkspaceResponse(WORKSPACE_ID, WORKSPACE_NAME, ACCESS_TOKEN));
            given(slackClient.requestBotAccessToken(AUTHORIZATION_CODE))
                .willReturn(new WorkspaceResponse(WORKSPACE_ID, WORKSPACE_NAME, ACCESS_TOKEN));
            authService.installSlackApp(AUTHORIZATION_CODE);

            assertThatNoException()
                .isThrownBy(() -> authService.installSlackApp(AUTHORIZATION_CODE));
        }
    }
}
