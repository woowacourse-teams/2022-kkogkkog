package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입을_하고;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.미등록_COFFEE_쿠폰_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.acceptance.support.AcceptanceTest;
import com.woowacourse.kkogkkog.coupon.presentation.dto.UnregisteredCouponsResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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

    static ExtractableResponse<Response> 미등록_쿠폰_생성을_요청한다(String token, Object data) {
        return invokePostWithToken("/api/v2/coupons/unregistered", token, data);
    }
}
