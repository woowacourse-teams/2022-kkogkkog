package com.woowacourse.kkogkkog.auth.application;

import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.auth.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.infrastructure.dto.GoogleUserInfo;
import com.woowacourse.kkogkkog.infrastructure.dto.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.dto.WorkspaceResponse;
import com.woowacourse.kkogkkog.infrastructure.exception.AccessTokenRetrievalFailedException;
import com.woowacourse.kkogkkog.member.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.support.application.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DisplayName("AuthService 클래스의")
class AuthServiceTest extends ServiceTest {

    private static final String AUTHORIZATION_CODE = "code";
    private static final String WORKSPACE_ID = "T03LX3C5540";
    private static final String WORKSPACE_NAME = "workspace_name";
    private static final String USER_ACCESS_TOKEN = "USER_ACCESS_TOKEN";
    private static final String BOT_ACCESS_TOKEN = "BOT_ACCESS_TOKEN";

    @Autowired
    private AuthService authService;

    @Nested
    @DisplayName("login 메서드는")
    class Login {

        @Test
        @DisplayName("임시 코드를 입력받으면, 토큰과 초기 사용자 여부를 반환한다.")
        void success() {
            MemberResponse memberResponse = MemberResponse.of(ROOKIE.getMember());
            WorkspaceResponse workspaceResponse = WorkspaceResponse.of(KKOGKKOG.getWorkspace());
            given(slackClient.requestAccessToken(AUTHORIZATION_CODE))
                .willReturn(USER_ACCESS_TOKEN);
            given(slackClient.requestUserInfo(USER_ACCESS_TOKEN))
                .willReturn(
                    new SlackUserInfo(
                        memberResponse.getUserId(),
                        workspaceResponse.getWorkspaceId(),
                        workspaceResponse.getWorkspaceName(),
                        memberResponse.getNickname(),
                        memberResponse.getEmail(),
                        memberResponse.getImageUrl()));

            TokenResponse tokenResponse = authService.login(AUTHORIZATION_CODE);

            assertThat(tokenResponse).isNotNull();
        }

        @Test
        @DisplayName("올바르지 않은 임시 코드를 입력하면, 예외를 던진다")
        void fail_invalidCode() {
            given(slackClient.requestAccessToken("invalid_code"))
                .willThrow(new AccessTokenRetrievalFailedException());

            assertThatThrownBy(
                () -> authService.login("invalid_code")
            ).isInstanceOf(AccessTokenRetrievalFailedException.class);
        }
    }

    @Nested
    @DisplayName("loginGoogle 메서드는")
    class LoginGoogle {

        @Test
        @DisplayName("임시 코드를 입력받으면, 토큰과 초기 사용자 여부를 반환한다.")
        void success() {
            MemberResponse memberResponse = MemberResponse.of(ROOKIE.getMember());
            given(googleClient.requestAccessToken(AUTHORIZATION_CODE))
                .willReturn(USER_ACCESS_TOKEN);
            given(googleClient.requestUserInfo(USER_ACCESS_TOKEN))
                .willReturn(
                    new GoogleUserInfo(
                        memberResponse.getNickname(),
                        memberResponse.getEmail(),
                        memberResponse.getImageUrl()));
            TokenResponse tokenResponse = authService.loginGoogle(AUTHORIZATION_CODE);

            assertThat(tokenResponse).isNotNull();
        }

        @Test
        @DisplayName("올바르지 않은 임시 코드를 입력하면, 예외를 던진다")
        void fail_invalidCode() {
            given(googleClient.requestAccessToken("invalid_code"))
                .willThrow(new AccessTokenRetrievalFailedException());

            assertThatThrownBy(
                () -> authService.loginGoogle("invalid_code")
            ).isInstanceOf(AccessTokenRetrievalFailedException.class);
        }
    }

    @Nested
    @DisplayName("installSlackApp 메서드는")
    class InstallSlackBot {

        @Test
        @DisplayName("임시 코드를 입력하면, 봇 토큰을 저장한다")
        void success() {
            MemberResponse memberResponse = MemberResponse.of(ROOKIE.getMember());
            WorkspaceResponse workspaceResponse = WorkspaceResponse.of(KKOGKKOG.getWorkspace());
            given(slackClient.requestAccessToken(AUTHORIZATION_CODE))
                .willReturn(USER_ACCESS_TOKEN);
            given(slackClient.requestUserInfo(USER_ACCESS_TOKEN))
                .willReturn(
                    new SlackUserInfo(
                        memberResponse.getUserId(),
                        workspaceResponse.getWorkspaceId(),
                        workspaceResponse.getWorkspaceName(),
                        memberResponse.getNickname(),
                        memberResponse.getEmail(),
                        memberResponse.getImageUrl()));
            authService.login(AUTHORIZATION_CODE);

            given(slackClient.requestBotAccessToken(AUTHORIZATION_CODE))
                .willReturn(new WorkspaceResponse(WORKSPACE_ID, WORKSPACE_NAME, BOT_ACCESS_TOKEN));

            assertThatNoException()
                .isThrownBy(() -> authService.installSlackApp(AUTHORIZATION_CODE));
        }
    }
}
