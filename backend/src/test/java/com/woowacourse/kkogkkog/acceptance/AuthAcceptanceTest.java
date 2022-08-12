package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokeGetWithQueryParams;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePost;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.fixture.WorkspaceFixture;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.WorkspaceResponse;
import com.woowacourse.kkogkkog.presentation.dto.InstallSlackAppRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String AUTHORIZATION_CODE = "CODE";

    @Test
    void 가입되지_않은_회원은_정보가_저장되고_로그인을_할_수_있다() {
        MemberResponse memberResponse = MemberResponse.of(ROOKIE.getMember());
        WorkspaceResponse workspaceResponse = WorkspaceResponse.of(WorkspaceFixture.WORKSPACE);

        Boolean actual = 회원가입_또는_로그인에_성공한다(memberResponse, workspaceResponse).getIsNew();

        assertThat(actual).isTrue();
    }

    @Test
    void 가입된_회원은_로그인을_할_수_있다() {
        MemberResponse memberResponse = MemberResponse.of(ROOKIE.getMember());
        WorkspaceResponse workspaceResponse = WorkspaceResponse.of(WorkspaceFixture.WORKSPACE);
        회원가입_또는_로그인에_성공한다(memberResponse, workspaceResponse);

        회원가입_또는_로그인에_성공한다(memberResponse, workspaceResponse);
        Boolean actual = 회원가입_또는_로그인에_성공한다(memberResponse, workspaceResponse).getIsNew();

        assertThat(actual).isFalse();
    }

    @Test
    void 슬랙_앱을_등록할_수_있다() {
        given(slackClient.requestBotAccessToken(AUTHORIZATION_CODE))
            .willReturn(
                new WorkspaceResponse("T03LX3C5540", "workspace_name", "ACCESS_TOKEN"));

        MemberResponse memberResponse = MemberResponse.of(ROOKIE.getMember());
        WorkspaceResponse workspaceResponse = WorkspaceResponse.of(WorkspaceFixture.WORKSPACE);
        회원가입_또는_로그인에_성공한다(memberResponse, workspaceResponse);
        ExtractableResponse<Response> extract = 슬랙_앱을_설치한다();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static TokenResponse 회원가입_또는_로그인에_성공한다(MemberResponse memberResponse,
                                                  WorkspaceResponse workspaceResponse) {
        given(slackClient.getUserInfoByCode(AUTHORIZATION_CODE))
            .willReturn(
                new SlackUserInfo(
                    memberResponse.getUserId(),
                    workspaceResponse.getWorkspaceId(),
                    workspaceResponse.getWorkspaceName(),
                    memberResponse.getNickname(),
                    memberResponse.getEmail(),
                    memberResponse.getImageUrl()));

        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("code", AUTHORIZATION_CODE);
        ExtractableResponse<Response> extract = invokeGetWithQueryParams("/api/login/token",
            queryParams);

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        return extract.as(TokenResponse.class);
    }

    private ExtractableResponse<Response> 슬랙_앱을_설치한다() {
        return invokePost("/api/install/bot", new InstallSlackAppRequest(AUTHORIZATION_CODE));
    }
}
