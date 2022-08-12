package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokeGetWithQueryParams;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePost;
import static com.woowacourse.kkogkkog.acceptance.MemberAcceptanceTest.프로필_수정을_성공한다;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.fixture.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.WorkspaceResponse;
import com.woowacourse.kkogkkog.presentation.dto.InstallSlackAppRequest;
import com.woowacourse.kkogkkog.presentation.dto.MemberUpdateMeRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String AUTHORIZATION_CODE = "ABC123";

    @Test
    void 가입되지_않은_회원은_새로운_정보가_저장되고_로그인을_할_수_있다() {
        TokenResponse tokenResponse = 회원가입_혹은_로그인을_한다(ROOKIE.getMember(KKOGKKOG.getWorkspace()));

        assertThat(tokenResponse.getIsNew()).isTrue();
        assertThat(tokenResponse.getAccessToken()).isNotBlank();
    }

    @Test
    void 가입된_회원은_로그인을_할_수_있다() {
        회원가입을_하고(ROOKIE.getMember(KKOGKKOG.getWorkspace()));
        TokenResponse tokenResponse = 회원가입_혹은_로그인을_한다(ROOKIE.getMember(KKOGKKOG.getWorkspace()));

        assertThat(tokenResponse.getIsNew()).isFalse();
        assertThat(tokenResponse.getAccessToken()).isNotBlank();
    }

    @Test
    void 슬랙_앱을_등록할_수_있다() {
        회원가입을_하고(ROOKIE.getMember(KKOGKKOG.getWorkspace()));
        ExtractableResponse<Response> extract = 슬랙_앱을_설치한다();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static String 회원가입_및_닉네임을_수정하고(Member member) {
        String accessToken = 회원가입을_하고(member);
        프로필_수정을_성공한다(accessToken, new MemberUpdateMeRequest(member.getNickname()));
        return accessToken;
    }

    public static String 회원가입을_하고(Member member) {
        return 회원가입_혹은_로그인을_한다(member).getAccessToken();
    }

    public static TokenResponse 회원가입_혹은_로그인을_한다(Member member) {
        given(slackClient.getUserInfoByCode(AUTHORIZATION_CODE))
            .willReturn(new SlackUserInfo(
                member.getUserId(),
                member.getWorkspace().getWorkspaceId(),
                member.getWorkspace().getName(),
                member.getNickname(),
                member.getEmail(),
                member.getImageUrl()));

        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("code", AUTHORIZATION_CODE);
        ExtractableResponse<Response> extract = invokeGetWithQueryParams("/api/login/token",
            queryParams);

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        return extract.as(TokenResponse.class);
    }

    private ExtractableResponse<Response> 슬랙_앱을_설치한다() {
        given(slackClient.requestBotAccessToken(AUTHORIZATION_CODE))
            .willReturn(WorkspaceResponse.of(KKOGKKOG.getWorkspace()));

        return invokePost("/api/install/bot", new InstallSlackAppRequest(AUTHORIZATION_CODE));
    }
}
