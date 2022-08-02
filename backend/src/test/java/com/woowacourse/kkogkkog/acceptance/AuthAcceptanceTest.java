package com.woowacourse.kkogkkog.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.fixture.MemberFixture;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
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

    public static TokenResponse 회원가입_또는_로그인에_성공한다(MemberResponse memberResponse) {
        given(slackClient.getUserInfoByCode(AUTHORIZATION_CODE))
            .willReturn(
                new SlackUserInfo(
                    memberResponse.getUserId(),
                    memberResponse.getWorkspaceId(),
                    memberResponse.getNickname(),
                    memberResponse.getEmail(),
                    memberResponse.getImageUrl()));

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
