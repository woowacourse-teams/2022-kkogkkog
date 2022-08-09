package com.woowacourse.kkogkkog.application;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.WorkspaceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DisplayName("WorkspaceServiceTest 클래스의")
public class WorkspaceServiceTest extends ServiceTest{

    private static final String AUTHORIZATION_CODE = "code";
    private static final String WORKSPACE_ID = "workspace_id";
    private static final String WORKSPACE_NAME = "workspace_name";
    private static final String ACCESS_TOKEN = "access_token";

    @Autowired
    private WorkspaceService workspaceService;

    @Nested
    @DisplayName("saveOrUpdate 메서드는")
    class saveOrUpdate {

        @Test
        @DisplayName("워크스페이스 정보를 저장 또는 업데이트 한다.")
        void success() {
            SlackUserInfo slackUserInfo = new SlackUserInfo("URookie", "루키", "rookie@gmail.com",
                "user_image", "workspace_id", "kkogkkog", "workspace_image");

            assertThatNoException()
                .isThrownBy(() -> workspaceService.saveOrUpdate(slackUserInfo));
        }
    }

    @Nested
    @DisplayName("installSlackApp 메서드는")
    class InstallSlackBot {

        @Test
        @DisplayName("임시 코드를 입력하면, 봇 토큰을 저장한다")
        void success() {
            SlackUserInfo slackUserInfo = new SlackUserInfo("URookie", "루키", "rookie@gmail.com",
                "user_image", WORKSPACE_ID, WORKSPACE_NAME, "workspace_image");
            given(slackClient.requestBotAccessToken(AUTHORIZATION_CODE))
                .willReturn(new WorkspaceResponse(WORKSPACE_ID, WORKSPACE_NAME, ACCESS_TOKEN));
            workspaceService.saveOrUpdate(slackUserInfo);

            assertThatNoException()
                .isThrownBy(() -> workspaceService.installSlackApp(AUTHORIZATION_CODE));
        }
    }
}
