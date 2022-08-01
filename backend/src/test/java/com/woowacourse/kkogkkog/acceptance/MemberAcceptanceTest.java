package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입_또는_로그인에_성공한다;
import static com.woowacourse.kkogkkog.fixture.MemberFixture.ARTHUR;
import static com.woowacourse.kkogkkog.fixture.MemberFixture.ROOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.presentation.dto.MemberNicknameUpdateRequest;
import com.woowacourse.kkogkkog.presentation.dto.SuccessResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원_가입된_전체_사용자_조회를_할_수_있다() {
        회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE));
        회원가입_또는_로그인에_성공한다(MemberResponse.of(ARTHUR));

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .when()
            .get("/api/members")
            .then().log().all()
            .extract();

        List<MemberResponse> members = extract.body().jsonPath()
            .getList("data", MemberResponse.class);
        SuccessResponse<List<MemberResponse>> membersResponse = new SuccessResponse<>(members);

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(membersResponse.getData()).hasSize(2),
            () -> assertThat(membersResponse.getData()).usingRecursiveComparison().isEqualTo(
                List.of(
                    MemberResponse.of(new Member(1L, "URookie", "T03LX3C5540",
                        "루키", "image")),
                    MemberResponse.of(new Member(2L, "UArthur", "T03LX3C5540",
                        "아서", "image"))
                )
            ));
    }

    @Test
    void 로그인_한_경우_본인의_정보를_조회할_수_있다() {
        String rookieAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE)).getAccessToken();
        회원가입_또는_로그인에_성공한다(MemberResponse.of(ARTHUR));

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .when()
            .auth().oauth2(rookieAccessToken)
            .get("/api/members/me")
            .then().log().all()
            .extract();

        MemberResponse memberResponse = extract.as(MemberResponse.class);

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(memberResponse).usingRecursiveComparison().isEqualTo(
                MemberResponse.of(new Member(1L, "URookie", "T03LX3C5540",
                    "루키", "image")))
        );
    }

    @Test
    void 본인의_닉네임을_수정할_수_있다() {
        String newNickname = "새로운_닉네임";
        String rookieAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE)).getAccessToken();

        RestAssured.given().log().all()
            .when()
            .auth().oauth2(rookieAccessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new MemberNicknameUpdateRequest(newNickname))
            .put("/api/members/me/nickname")
            .then().log().all()
            .extract();

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .when()
            .auth().oauth2(rookieAccessToken)
            .get("/api/members/me")
            .then().log().all()
            .extract();

        String actual = extract.as(MemberResponse.class).getNickname();

        assertThat(actual).isEqualTo(newNickname);
    }
}
