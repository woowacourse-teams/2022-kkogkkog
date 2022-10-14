package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입을_하고;
import static com.woowacourse.kkogkkog.acceptance.CouponAcceptanceTest.쿠폰코드로_쿠폰_생성을_요청하고;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeDeleteWithToken;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeGetWithQueryParams;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeGetWithToken;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeGetWithTokenAndQueryParams;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.coupon.domain.UnregisteredCouponStatus.REGISTERED;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.미등록_COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.쿠폰_코드_등록_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.acceptance.support.AcceptanceTest;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponResponse;
import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCouponStatus;
import com.woowacourse.kkogkkog.coupon.presentation.dto.UnregisteredCouponsResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class UnregisteredCouponAcceptanceTest extends AcceptanceTest {

    @Test
    void 미등록_쿠폰_생성을_할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        final var extract = 미등록_쿠폰_생성을_요청한다(senderToken, 미등록_COFFEE_쿠폰_생성_요청(5));

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

        final var extract = 회원의_미동록_쿠폰_목록들을_조회한다(senderToken);

        UnregisteredCouponsResponse actual = extract.as(UnregisteredCouponsResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(actual.getData()).hasSize(2)
        );
    }

    @Test
    void 회원은_미등록_쿠폰을_상태에_따라_조회할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        String receiverToken = 회원가입을_하고(LEO.getMember());
        UnregisteredCouponsResponse unregisteredCouponsResponse = 미등록_쿠폰_생성을_요청하고(senderToken, 미등록_COFFEE_쿠폰_생성_요청(4));
        String couponCode = unregisteredCouponsResponse.getData().get(0).getCouponCode();
        쿠폰코드로_쿠폰_생성을_요청하고(receiverToken, 쿠폰_코드_등록_요청(couponCode));

        final var extract = 회원의_미동록_쿠폰_목록들을_상태별로_조회한다(senderToken, REGISTERED);

        UnregisteredCouponsResponse response = extract.as(UnregisteredCouponsResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(response.getData()).hasSize(1)
        );
    }

    @Test
    void 단일_미등록_쿠폰을_아이디로_상세_조회할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        UnregisteredCouponsResponse response = 미등록_쿠폰_생성을_요청하고(senderToken,
            미등록_COFFEE_쿠폰_생성_요청(1));
        Long unregisteredCouponId = response.getData().get(0).getId();

        final var extract = 아이디로_회원의_단일_미등록_쿠폰_상세정보를_조회한다(senderToken, unregisteredCouponId);

        UnregisteredCouponResponse actual = extract.as(UnregisteredCouponResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(actual.getId()).isEqualTo(unregisteredCouponId)
        );
    }

    @Test
    void 단일_미등록_쿠폰을_쿠폰코드로_상세_조회할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        UnregisteredCouponsResponse response = 미등록_쿠폰_생성을_요청하고(senderToken,
            미등록_COFFEE_쿠폰_생성_요청(1));
        String couponCode = response.getData().get(0).getCouponCode();

        final var extract = 쿠폰코드로_단일_미등록_쿠폰_상세정보를_조회한다(couponCode);

        UnregisteredCouponResponse actual = extract.as(
            UnregisteredCouponResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(actual.getCouponCode()).isEqualTo(couponCode)
        );
    }

    @Test
    void 회원은_미등록_쿠폰을_삭제할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        UnregisteredCouponsResponse response = 미등록_쿠폰_생성을_요청하고(senderToken,
            미등록_COFFEE_쿠폰_생성_요청(1));
        Long unregisteredCouponId = response.getData().get(0).getId();

        final var extract = 회원의_미등록_쿠폰을_삭제한다(senderToken, unregisteredCouponId);

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    static ExtractableResponse<Response> 미등록_쿠폰_생성을_요청한다(String token, Object data) {
        return invokePostWithToken("/api/v2/coupons/unregistered", token, data);
    }

    public static UnregisteredCouponsResponse 미등록_쿠폰_생성을_요청하고(String token, Object data) {
        final var response = invokePostWithToken("/api/v2/coupons/unregistered",
            token, data);
        return response.as(UnregisteredCouponsResponse.class);
    }

    static ExtractableResponse<Response> 회원의_미동록_쿠폰_목록들을_조회한다(String token) {
        return invokeGetWithToken("/api/v2/coupons/unregistered", token);
    }

    static ExtractableResponse<Response> 회원의_미동록_쿠폰_목록들을_상태별로_조회한다(String token, UnregisteredCouponStatus status) {
        return invokeGetWithTokenAndQueryParams("/api/v2/coupons/unregistered/status", token,
            Map.of("type", status.toString()));
    }

    static ExtractableResponse<Response> 아이디로_회원의_단일_미등록_쿠폰_상세정보를_조회한다(String token,
                                                                       Long unRegisteredCouponId) {
        return invokeGetWithToken("/api/v2/coupons/unregistered/" + unRegisteredCouponId, token);
    }

    static ExtractableResponse<Response> 쿠폰코드로_단일_미등록_쿠폰_상세정보를_조회한다(String couponCode) {
        return invokeGetWithQueryParams("/api/v2/coupons/unregistered/code",
            Map.of("couponCode", couponCode));
    }

    static ExtractableResponse<Response> 회원의_미등록_쿠폰을_삭제한다(String token, Long unRegisteredCouponId) {
        return invokeDeleteWithToken("/api/v2/coupons/unregistered/" + unRegisteredCouponId, token);
    }
}
