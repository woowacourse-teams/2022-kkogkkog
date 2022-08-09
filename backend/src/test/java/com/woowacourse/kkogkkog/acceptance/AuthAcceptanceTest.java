package com.woowacourse.kkogkkog.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.fixture.MemberFixture;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import com.woowacourse.kkogkkog.infrastructure.WorkspaceResponse;
import com.woowacourse.kkogkkog.presentation.dto.InstallSlackAppRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    private static final String AUTHORIZATION_CODE = "CODE";

    @Test
    void 가입되지_않은_회원은_정보가_저장되고_로그인을_할_수_있다() {
        MemberResponse memberResponse = MemberResponse.of(MemberFixture.ROOKIE);

        Boolean actual = 회원가입_또는_로그인에_성공한다(memberResponse).getIsNew();

        assertThat(actual).isTrue();
    }

    @Test
    void 가입된_회원은_로그인을_할_수_있다() {
        MemberResponse memberResponse = MemberResponse.of(MemberFixture.ROOKIE);

        회원가입_또는_로그인에_성공한다(memberResponse);
        Boolean actual = 회원가입_또는_로그인에_성공한다(memberResponse).getIsNew();

        assertThat(actual).isFalse();
    }

    @Test
    void 슬랙_앱을_등록할_수_있다() {
        given(slackClient.requestBotAccessToken(AUTHORIZATION_CODE))
            .willReturn(
                new WorkspaceResponse("T03LX3C5540", "workspace_name", "ACCESS_TOKEN"));

        MemberResponse memberResponse = MemberResponse.of(MemberFixture.ROOKIE);
        회원가입_또는_로그인에_성공한다(memberResponse);

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .body(new InstallSlackAppRequest(AUTHORIZATION_CODE))
            .post("/api/install/bot")
            .then().log().all()
            .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static TokenResponse 회원가입_또는_로그인에_성공한다(MemberResponse memberResponse) {
        given(slackClient.getUserInfoByCode(AUTHORIZATION_CODE))
            .willReturn(
                new SlackUserInfo(
                    memberResponse.getUserId(),
                    memberResponse.getWorkspaceId(),
                    memberResponse.getNickname(),
                    memberResponse.getEmail(),
                    memberResponse.getImageUrl(),
                    "workspace_name"));

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .queryParam("code", AUTHORIZATION_CODE)
            .get("/api/login/token")
            .then().log().all()
            .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        return extract.as(TokenResponse.class);
    }
}
