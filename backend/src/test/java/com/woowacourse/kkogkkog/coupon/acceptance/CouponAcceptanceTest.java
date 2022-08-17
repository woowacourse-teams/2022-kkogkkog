package com.woowacourse.kkogkkog.coupon.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokeGet;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokeGetWithToken;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입을_하고;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.acceptance.AcceptanceTest;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsCreateResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.MyCouponsReservationResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class CouponAcceptanceTest extends AcceptanceTest {

    @Test
    void 쿠폰_생성을_할_수_있다() {
        회원가입을_하고(LEO.getMember());
        회원가입을_하고(AUTHOR.getMember());
        String accessToken = 회원가입을_하고(JEONG.getMember());

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
        회원가입을_하고(LEO.getMember());
        회원가입을_하고(AUTHOR.getMember());
        String accessToken = 회원가입을_하고(JEONG.getMember());
        쿠폰_생성을_요청하고(accessToken, COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));

        ExtractableResponse<Response> extract = 회원의_보낸쿠폰_받은쿠폰_목록들을_조회한다(accessToken);

        MyCouponsReservationResponse actual = extract.as(MyCouponsReservationResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(actual.getData().getSent()).hasSize(2),
            () -> assertThat(actual.getData().getReceived()).hasSize(0)
        );
    }

    @Test
    void 단일_쿠폰을_상세_조회할_수_있다() {
        회원가입을_하고(LEO.getMember());
        회원가입을_하고(AUTHOR.getMember());
        String accessToken = 회원가입을_하고(JEONG.getMember());
        ExtractableResponse<Response> extractableResponse = 쿠폰_생성을_요청한다(accessToken,
            COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));

        CouponsCreateResponse couponsCreateResponse = extractableResponse.as(
            CouponsCreateResponse.class);
        ExtractableResponse<Response> extract = 회원의_단일쿠폰_상세정보를_조회한다(
            couponsCreateResponse.getData().get(0).getId());

        CouponDetailResponse couponDetailResponse = extract.as(CouponDetailResponse.class);

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(couponDetailResponse.getCouponStatus()).isEqualTo(
                CouponStatus.READY.name()),
            () -> assertThat(couponDetailResponse.getCouponHistories()).hasSize(1)
        );

    }

    public ExtractableResponse<Response> 쿠폰_생성을_요청한다(String token, Object data) {
        return invokePostWithToken("/api/coupons", token, data);
    }

    public ExtractableResponse<Response> 회원의_보낸쿠폰_받은쿠폰_목록들을_조회한다(String token) {
        return invokeGetWithToken("/api/coupons", token);
    }

    public ExtractableResponse<Response> 회원의_단일쿠폰_상세정보를_조회한다(Long couponId) {
        return invokeGet("/api/coupons/" + couponId);
    }

    public static void 쿠폰_생성을_요청하고(String token, Object data) {
        invokePostWithToken("/api/coupons", token, data);
    }
}
