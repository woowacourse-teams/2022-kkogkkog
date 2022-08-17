package com.woowacourse.kkogkkog.core.reservation.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePutWithToken;
import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입을_하고;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.ReservationDtoFixture.예약_변경_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.ReservationDtoFixture.예약_생성_요청;
import static com.woowacourse.kkogkkog.core.coupon.acceptance.CouponAcceptanceTest.쿠폰_생성을_요청하고;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.acceptance.AcceptanceTest;
import com.woowacourse.kkogkkog.reservation.presentation.dto.ReservationCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ReservationAcceptanceTest extends AcceptanceTest {

    @Test
    void 예약을_신청할_수_있다() {
        String rookieToken = 회원가입을_하고(ROOKIE.getMember());
        String leoToken = 회원가입을_하고(LEO.getMember());
        쿠폰_생성을_요청하고(rookieToken, COFFEE_쿠폰_생성_요청(List.of(2L)));

        ExtractableResponse<Response> extract = 예약을_신청한다(
            leoToken, 예약_생성_요청(1L, LocalDate.now()));

        assertAll(
            () -> assertThat(extract.header("Location").split("/")[3]).isEqualTo("1"),
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }

    @Test
    void 예약_상태를_변경할_수_있다() {
        String rookieToken = 회원가입을_하고(ROOKIE.getMember());
        String leoToken = 회원가입을_하고(LEO.getMember());
        쿠폰_생성을_요청하고(rookieToken, COFFEE_쿠폰_생성_요청(List.of(2L)));
        예약을_신청하고(leoToken, 예약_생성_요청(1L, LocalDate.now()));

        ExtractableResponse<Response> extract = 예약을_변경한다(
            1L, rookieToken, 예약_변경_요청("ACCEPT"));

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    public ExtractableResponse<Response> 예약을_변경한다(Long id, String token, Object data) {
        return invokePutWithToken("/api/reservations/" + id, token, data);
    }

    public static void 예약을_신청하고(String token, ReservationCreateRequest request) {
        invokePostWithToken("/api/reservations", token, request);
    }

    public static ExtractableResponse<Response> 예약을_신청한다(String token, Object data) {
        return invokePostWithToken("/api/reservations", token, data);
    }
}
