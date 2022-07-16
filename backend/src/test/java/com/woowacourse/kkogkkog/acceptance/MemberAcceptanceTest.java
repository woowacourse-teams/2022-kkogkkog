package com.woowacourse.kkogkkog.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.MembersResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
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
    void 회원_가입을_할_수_있다() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("rookie@gmail.com",
            "password1234!", "rookie");

        회원_가입에_성공한다(memberCreateRequest);
    }

    public static void 회원_가입에_성공한다(MemberCreateRequest memberCreateRequest) {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .body(memberCreateRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/members")
            .then().log().all()
            .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 회원_가입을_할때_이메일이_중복된_경우_실패한다() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("rookie@gmail.com",
            "password1234!", "rookie");
        회원_가입에_성공한다(memberCreateRequest);

        회원_가입에_실패한다(memberCreateRequest);
    }

    private void 회원_가입에_실패한다(MemberCreateRequest memberCreateRequest) {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .body(memberCreateRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/members")
            .then().log().all()
            .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 회원_가입된_전체_사용자_조회를_할_수_있다() {
        MemberCreateRequest memberCreateRequest1 = new MemberCreateRequest("rookie@gmail.com",
            "password1234!", "rookie");
        MemberCreateRequest memberCreateRequest2 = new MemberCreateRequest("arthur@gmail.com",
            "password1234!", "arthur");
        회원_가입에_성공한다(memberCreateRequest1);
        회원_가입에_성공한다(memberCreateRequest2);

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .when()
            .get("/api/members")
            .then().log().all()
            .extract();

        MembersResponse membersResponse = extract.as(MembersResponse.class);

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(membersResponse.getData()).hasSize(2),
            () -> assertThat(membersResponse.getData()).usingRecursiveComparison().isEqualTo(
                List.of(
                    MemberResponse.of(new Member(1L, "rookie@gmail.com", null, "rookie")),
                    MemberResponse.of(new Member(2L, "arthur@gmail.com", null, "arthur"))
                )
        ));
    }
}
