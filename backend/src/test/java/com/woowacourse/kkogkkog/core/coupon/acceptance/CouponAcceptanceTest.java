package com.woowacourse.kkogkkog.core.coupon.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokeGetWithToken;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.acceptance.AcceptanceTest;
import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsCreateResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.MyCouponsReservationResponse;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class CouponAcceptanceTest extends AcceptanceTest {

    @Test
    void 쿠폰_생성을_할_수_있다() {
        로그인을_하고(MemberResponse.of(LEO.getMember()));
        로그인을_하고(MemberResponse.of(AUTHOR.getMember()));
        String accessToken = 로그인을_하고(MemberResponse.of(JEONG.getMember()));

        ExtractableResponse<Response> extract = 쿠폰_생성을_요청한다(
            accessToken, COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));

        CouponsCreateResponse actual = extract.response().as(CouponsCreateResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(actual.getData()).hasSize(2)
        );
    }

    @Test
    void 회원의_보낸쿠폰과_받은쿠폰을_조회_할_수_있다() {
        로그인을_하고(MemberResponse.of(LEO.getMember()));
        로그인을_하고(MemberResponse.of(AUTHOR.getMember()));
        String accessToken = 로그인을_하고(MemberResponse.of(JEONG.getMember()));
        쿠폰_생성을_요청하고(accessToken, COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));

        ExtractableResponse<Response> extract = 회원의_보낸쿠폰_받은쿠폰_목록들을_조회한다(accessToken);

        MyCouponsReservationResponse actual = extract.as(MyCouponsReservationResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(actual.getData().getSent()).hasSize(2),
            () -> assertThat(actual.getData().getReceived()).hasSize(0)
        );
    }

    public ExtractableResponse<Response> 쿠폰_생성을_요청한다(String token, Object data) {
        return invokePostWithToken("/api/coupons", token, data);
    }

    public ExtractableResponse<Response> 회원의_보낸쿠폰_받은쿠폰_목록들을_조회한다(String token) {
        return invokeGetWithToken("/api/coupons", token);
    }

    public static void 쿠폰_생성을_요청하고(String token, Object data) {
        invokePostWithToken("/api/coupons", token, data);
    }

    // 임시로 다음위치에 이동함 (리팩토링 대상)
    private String 로그인을_하고(MemberResponse memberResponse) {
        given(slackClient.getUserInfoByCode("AUTHORIZATION_CODE"))
            .willReturn(
                new SlackUserInfo(
                    memberResponse.getUserId(),
                    null,
                    null,
                    memberResponse.getNickname(),
                    memberResponse.getEmail(),
                    memberResponse.getImageUrl()));

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .queryParam("code", "AUTHORIZATION_CODE")
            .get("/api/login/token")
            .then().log().all()
            .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        return extract.as(TokenResponse.class).getAccessToken();
    }
}
