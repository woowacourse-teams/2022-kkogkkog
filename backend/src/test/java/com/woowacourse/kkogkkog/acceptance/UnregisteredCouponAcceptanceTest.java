package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입을_하고;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeGet;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeGetWithToken;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.미등록_COFFEE_쿠폰_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.acceptance.support.AcceptanceTest;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.UnregisteredCouponsResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class UnregisteredCouponAcceptanceTest extends AcceptanceTest {

    @Test
    void 미등록_쿠폰_생성을_할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        var extract = 미등록_쿠폰_생성을_요청한다(senderToken, 미등록_COFFEE_쿠폰_생성_요청(5));

        UnregisteredCouponsResponse actual = extract.as(UnregisteredCouponsResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(actual.getData()).hasSize(5)
        );
    }

    @Test
    void 회원의_미등록_쿠폰_조회를_할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        미등록_쿠폰_생성을_요청하고(senderToken, 미등록_COFFEE_쿠폰_생성_요청(2));

        var extract = 회원의_미동록_쿠폰_목록들을_조회한다(senderToken);

        UnregisteredCouponsResponse actual = extract.as(UnregisteredCouponsResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(actual.getData()).hasSize(2)
        );
    }

    @Test
    void 단일_미등록_쿠폰을_아이디로_상세_조회할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        UnregisteredCouponsResponse response = 미등록_쿠폰_생성을_요청하고(senderToken,
            미등록_COFFEE_쿠폰_생성_요청(1));
        Long unregisteredCouponId = response.getData().get(0).getId();

        var extract = 아이디로_회원의_단일_미등록_쿠폰_상세정보를_조회한다(senderToken, unregisteredCouponId);

        UnregisteredCouponDetailResponse actual = extract.as(UnregisteredCouponDetailResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(actual.getId()).isEqualTo(unregisteredCouponId)
        );
    }

    static ExtractableResponse<Response> 미등록_쿠폰_생성을_요청한다(String token, Object data) {
        return invokePostWithToken("/api/v2/coupons/unregistered", token, data);
    }

    static UnregisteredCouponsResponse 미등록_쿠폰_생성을_요청하고(String token, Object data) {
        ExtractableResponse<Response> response = invokePostWithToken("/api/v2/coupons/unregistered",
            token, data);
        return response.as(UnregisteredCouponsResponse.class);
    }

    static ExtractableResponse<Response> 회원의_미동록_쿠폰_목록들을_조회한다(String token) {
        return invokeGetWithToken("/api/v2/coupons/unregistered", token);
    }

    static ExtractableResponse<Response> 아이디로_회원의_미등록_단일쿠폰_상세정보를_조회한다(String token,
                                                                      Long unRegisteredCouponId) {
        return invokeGetWithToken("/api/v2/coupons/unregistered/" + unRegisteredCouponId, token);
    }
}
