package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeGetWithQueryParams;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokePost;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.acceptance.support.AcceptanceTest;
import com.woowacourse.kkogkkog.auth.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.auth.presentation.dto.InstallSlackAppRequest;
import com.woowacourse.kkogkkog.infrastructure.dto.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.dto.WorkspaceResponse;
import com.woowacourse.kkogkkog.member.application.dto.MemberCreateResponse;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.presentation.dto.MemberCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String AUTHORIZATION_CODE = "ABC123";
    private static final String USER_ACCESS_TOKEN = "USER_ACCESS_TOKEN";

    @Test
    void 회원가입을_할_수_있다() {
        String accessToken = 회원가입을_하고(ROOKIE.getMember(KKOGKKOG.getWorkspace()));

        assertThat(accessToken).isNotBlank();
    }

    @Test
    void 가입된_회원은_로그인을_할_수_있다() {
        회원가입을_하고(ROOKIE.getMember());

        TokenResponse tokenResponse = 로그인을_한다(ROOKIE.getMember(KKOGKKOG.getWorkspace()));

        Assertions.assertAll(
            () -> assertThat(tokenResponse.getIsNew()).isFalse(),
            () -> assertThat(tokenResponse.getAccessToken()).isNotBlank()
        );
    }

    @Test
    void 슬랙_앱을_등록할_수_있다() {
        회원가입을_하고(ROOKIE.getMember(KKOGKKOG.getWorkspace()));
        final var extract = 슬랙_앱을_설치한다();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static String 회원가입을_하고(Member member) {
        given(slackClient.requestUserInfo(any()))
            .willReturn(new SlackUserInfo(
                member.getUserId(),
                member.getWorkspace().getWorkspaceId(),
                member.getWorkspace().getName(),
                member.getNickname(),
                member.getEmail(),
                member.getImageUrl()));

        final var response = 회원가입을_요청한다(
            new MemberCreateRequest(USER_ACCESS_TOKEN, member.getNickname()));
        return response.as(MemberCreateResponse.class).getAccessToken();
    }

    public static TokenResponse 로그인을_한다(Member member) {
        given(slackClient.requestAccessToken(AUTHORIZATION_CODE))
            .willReturn(USER_ACCESS_TOKEN);
        given(slackClient.requestUserInfo(USER_ACCESS_TOKEN))
            .willReturn(new SlackUserInfo(
                member.getUserId(),
                member.getWorkspace().getWorkspaceId(),
                member.getWorkspace().getName(),
                member.getNickname(),
                member.getEmail(),
                member.getImageUrl()));

        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("code", AUTHORIZATION_CODE);
        final var response = invokeGetWithQueryParams("/api/v2/login/token", queryParams);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        return response.as(TokenResponse.class);
    }

    private ExtractableResponse<Response> 슬랙_앱을_설치한다() {
        given(slackClient.requestBotAccessToken(AUTHORIZATION_CODE))
            .willReturn(WorkspaceResponse.of(KKOGKKOG.getWorkspace()));
        return invokePost("/api/v2/install/bot", new InstallSlackAppRequest(AUTHORIZATION_CODE));
    }

    static ExtractableResponse<Response> 회원가입을_요청한다(Object data) {
        return invokePost("/api/v2/signup/token", data);
    }
}
