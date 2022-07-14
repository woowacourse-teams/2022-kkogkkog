package com.woowacourse.kkogkkog.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import com.woowacourse.kkogkkog.presentation.dto.TokenRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 로그인_할_수_있다() {
        MemberAcceptanceTest.회원_가입에_성공한다(new MemberCreateRequest("rookie@gmail.com",
                "password1234!", "rookie"));
        TokenRequest tokenRequest = new TokenRequest("rookie@gmail.com", "password1234!");

        로그인에_성공한다(tokenRequest);
    }

    private void 로그인에_성공한다(TokenRequest tokenRequest) {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .body(tokenRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/login")
                .then().log().all()
                .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
