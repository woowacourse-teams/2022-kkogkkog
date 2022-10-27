package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입을_하고;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeDeleteWithToken;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeGetWithQueryParams;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeGetWithToken;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeGetWithTokenAndQueryParams;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus.REGISTERED;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.support.fixture.dto.LazyCouponDtoFixture.미등록_COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.LazyCouponDtoFixture.쿠폰_코드_등록_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.acceptance.support.AcceptanceTest;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponResponse;
import com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus;
import com.woowacourse.kkogkkog.lazycoupon.presentation.dto.LazyCouponsResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class LazyCouponAcceptanceTest extends AcceptanceTest {

    @Test
    void 미등록_쿠폰_생성을_할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        final var response = 미등록_쿠폰_생성을_요청한다(senderToken, 미등록_COFFEE_쿠폰_생성_요청(5));

        LazyCouponsResponse actual = response.as(LazyCouponsResponse.class);
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(actual.getData()).hasSize(5)
        );
    }

    @Test
    void 미등록_쿠폰으로_쿠폰_생성을_할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        String receiverToken = 회원가입을_하고(AUTHOR.getMember());
        LazyCouponsResponse lazyCouponsResponse = 미등록_쿠폰_생성을_요청하고(senderToken, 미등록_COFFEE_쿠폰_생성_요청(1));
        String couponCode = lazyCouponsResponse.getData().get(0).getCouponCode();

        var response = 쿠폰코드로_쿠폰_생성을_요청한다(receiverToken, 쿠폰_코드_등록_요청(couponCode));

        CouponResponse actual = response.as(CouponResponse.class);
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(actual.getId()).isNotNull()
        );
    }

    @Test
    void 회원은_미등록_쿠폰을_상태에_따라_조회할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        String receiverToken = 회원가입을_하고(LEO.getMember());
        LazyCouponsResponse lazyCouponsResponse = 미등록_쿠폰_생성을_요청하고(senderToken, 미등록_COFFEE_쿠폰_생성_요청(4));
        String couponCode = lazyCouponsResponse.getData().get(0).getCouponCode();
        쿠폰코드로_쿠폰_생성을_요청하고(receiverToken, 쿠폰_코드_등록_요청(couponCode));

        final var response = 회원의_미동록_쿠폰_목록들을_상태별로_조회한다(senderToken, REGISTERED);
        LazyCouponsResponse responseBody = response.as(LazyCouponsResponse.class);
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(responseBody.getData()).hasSize(1)
        );
    }

    @Test
    void 단일_미등록_쿠폰을_아이디로_상세_조회할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        LazyCouponsResponse lazyCouponsResponse = 미등록_쿠폰_생성을_요청하고(senderToken, 미등록_COFFEE_쿠폰_생성_요청(1));
        Long lazyCouponId = lazyCouponsResponse.getData().get(0).getId();

        final var response = 아이디로_회원의_단일_미등록_쿠폰_상세정보를_조회한다(senderToken, lazyCouponId);

        LazyCouponResponse actual = response.as(LazyCouponResponse.class);
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(actual.getId()).isEqualTo(lazyCouponId)
        );
    }

    @Test
    void 단일_미등록_쿠폰을_쿠폰코드로_상세_조회할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        LazyCouponsResponse lazyCouponsResponse = 미등록_쿠폰_생성을_요청하고(senderToken, 미등록_COFFEE_쿠폰_생성_요청(1));
        String couponCode = lazyCouponsResponse.getData().get(0).getCouponCode();

        final var response = 쿠폰코드로_단일_미등록_쿠폰_상세정보를_조회한다(couponCode);

        LazyCouponResponse actual = response.as(LazyCouponResponse.class);
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(actual.getCouponCode()).isEqualTo(couponCode)
        );
    }

    @Test
    void 회원은_미등록_쿠폰을_삭제할_수_있다() {
        String senderToken = 회원가입을_하고(JEONG.getMember());
        LazyCouponsResponse lazyCouponsResponse = 미등록_쿠폰_생성을_요청하고(senderToken, 미등록_COFFEE_쿠폰_생성_요청(1));
        Long lazyCouponId = lazyCouponsResponse.getData().get(0).getId();

        final var response = 회원의_미등록_쿠폰을_삭제한다(senderToken, lazyCouponId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    static ExtractableResponse<Response> 미등록_쿠폰_생성을_요청한다(String token, Object data) {
        return invokePostWithToken("/api/v2/lazy-coupons", token, data);
    }

    public static LazyCouponsResponse 미등록_쿠폰_생성을_요청하고(String token, Object data) {
        final var response = invokePostWithToken("/api/v2/lazy-coupons", token, data);
        return response.as(LazyCouponsResponse.class);
    }

    static CouponResponse 쿠폰코드로_쿠폰_생성을_요청하고(String token, Object data) {
        final var response = invokePostWithToken("/api/v2/lazy-coupons/register", token, data);
        return response.as(CouponResponse.class);
    }


    static ExtractableResponse<Response> 쿠폰코드로_쿠폰_생성을_요청한다(String token, Object data) {
        return invokePostWithToken("/api/v2/lazy-coupons/register", token, data);
    }

    static ExtractableResponse<Response> 회원의_미동록_쿠폰_목록들을_상태별로_조회한다(String token, LazyCouponStatus status) {
        return invokeGetWithTokenAndQueryParams("/api/v2/lazy-coupons/status", token,
            Map.of("type", status.toString()));
    }

    static ExtractableResponse<Response> 아이디로_회원의_단일_미등록_쿠폰_상세정보를_조회한다(String token,
                                                                       Long lazyCouponId) {
        return invokeGetWithToken("/api/v2/lazy-coupons/" + lazyCouponId, token);
    }

    static ExtractableResponse<Response> 쿠폰코드로_단일_미등록_쿠폰_상세정보를_조회한다(String couponCode) {
        return invokeGetWithQueryParams("/api/v2/lazy-coupons/code",
            Map.of("couponCode", couponCode));
    }

    static ExtractableResponse<Response> 회원의_미등록_쿠폰을_삭제한다(String token, Long lazyCouponId) {
        return invokeDeleteWithToken("/api/v2/lazy-coupons/" + lazyCouponId, token);
    }
}
