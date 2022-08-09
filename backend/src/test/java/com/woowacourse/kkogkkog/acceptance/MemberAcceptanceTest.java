package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입_또는_로그인에_성공한다;
import static com.woowacourse.kkogkkog.acceptance.CouponAcceptanceTest.쿠폰_발급에_성공한다;
import static com.woowacourse.kkogkkog.fixture.MemberFixture.ARTHUR;
import static com.woowacourse.kkogkkog.fixture.MemberFixture.ROOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.MyProfileResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.presentation.dto.MemberHistoriesResponse;
import com.woowacourse.kkogkkog.presentation.dto.MemberUpdateMeRequest;
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

        ExtractableResponse<Response> extract = 전체_사용자_조회를_요청한다();
        List<MemberResponse> members = extract.body().jsonPath()
            .getList("data", MemberResponse.class);
        SuccessResponse<List<MemberResponse>> membersResponse = new SuccessResponse<>(members);

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(membersResponse.getData()).hasSize(2),
            () -> assertThat(membersResponse.getData()).usingRecursiveComparison().isEqualTo(
                List.of(
                    MemberResponse.of(new Member(1L, "URookie", "T03LX3C5540",
                        "루키", "rookie@gmail.com", "image")),
                    MemberResponse.of(new Member(2L, "UArthur", "T03LX3C5540",
                        "아서", "arthur@gmail.com", "image"))
                )
            ));
    }

    @Test
    void 로그인_한_경우_본인의_정보를_조회할_수_있다() {
        String rookieAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE)).getAccessToken();
        회원가입_또는_로그인에_성공한다(MemberResponse.of(ARTHUR));

        ExtractableResponse<Response> extract = 본인_정보_조회를_요청한다(rookieAccessToken);
        MyProfileResponse memberResponse = extract.as(MyProfileResponse.class);

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(memberResponse).usingRecursiveComparison().isEqualTo(
                new MyProfileResponse(1L, "URookie", "T03LX3C5540", "꼭꼭",
                    "루키", "rookie@gmail.com", "image", 0L))
        );
    }

    @Test
    void 회원에게_전달된_알림들을_조회할_수_있다() {
        Member ROOKIE = new Member(1L, "URookie", "T03LX3C5540", "루키", "rookie@gmail.com", "image");
        Member ARTHUR = new Member(2L, "UArthur", "T03LX3C5540", "아서", "arthur@gmail.com", "image");
        String rookieAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE)).getAccessToken();
        String arthurAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ARTHUR)).getAccessToken();
        쿠폰_발급에_성공한다(rookieAccessToken, List.of(ARTHUR));

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .when()
            .auth().oauth2(arthurAccessToken)
            .get("/api/members/me/histories")
            .then().log().all()
            .extract();

        MemberHistoriesResponse memberHistoriesResponse = extract.as(MemberHistoriesResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(memberHistoriesResponse.getData()).hasSize(1)
        );
    }

    @Test
    void 조회하지_않은_알림이면_알림의_방문상태가_변경된다() {
        Member ROOKIE = new Member(1L, "URookie", "T03LX3C5540", "루키", "rookie@gmail.com", "image");
        Member ARTHUR = new Member(2L, "UArthur", "T03LX3C5540", "아서", "arthur@gmail.com", "image");
        String rookieAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE)).getAccessToken();
        String arthurAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ARTHUR)).getAccessToken();
        쿠폰_발급에_성공한다(rookieAccessToken, List.of(ARTHUR));

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .when()
            .auth().oauth2(arthurAccessToken)
            .patch("/api/members/me/histories/1")
            .then().log().all()
            .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 본인의_닉네임을_수정할_수_있다() {
        String newNickname = "새로운_닉네임";
        String rookieAccessToken = 회원가입_또는_로그인에_성공한다(MemberResponse.of(ROOKIE)).getAccessToken();

        프로필_수정을_성공하고(newNickname, rookieAccessToken);
        ExtractableResponse<Response> extract = 본인_정보_조회를_요청한다(rookieAccessToken);
        String actual = extract.as(MyProfileResponse.class).getNickname();

        assertThat(actual).isEqualTo(newNickname);
    }

    private ExtractableResponse<Response> 본인_정보_조회를_요청한다(String accessToken) {
        return RestAssured.given().log().all()
            .when()
            .auth().oauth2(accessToken)
            .get("/api/members/me")
            .then().log().all()
            .extract();
    }

    private ExtractableResponse<Response> 전체_사용자_조회를_요청한다() {
        return RestAssured.given().log().all()
            .when()
            .get("/api/members")
            .then().log().all()
            .extract();
    }

    private void 프로필_수정을_성공하고(String nickname, String accessToken) {
        RestAssured.given().log().all()
            .when()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new MemberUpdateMeRequest(nickname))
            .put("/api/members/me")
            .then().log().all()
            .extract();
    }
}
