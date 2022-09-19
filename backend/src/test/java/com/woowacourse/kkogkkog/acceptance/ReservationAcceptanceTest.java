package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입을_하고;
import static com.woowacourse.kkogkkog.acceptance.LegacyCouponAcceptanceTest.COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.acceptance.LegacyCouponAcceptanceTest.쿠폰_생성을_요청하고;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokePutWithToken;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.ROOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.acceptance.support.AcceptanceTest;
import com.woowacourse.kkogkkog.reservation.presentation.dto.ReservationChangeRequest;
import com.woowacourse.kkogkkog.reservation.presentation.dto.ReservationCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

// TODO: should be deleted
@SuppressWarnings("NonAsciiCharacters")
public class ReservationAcceptanceTest extends AcceptanceTest {

    @Test
    void 예약을_신청할_수_있다() {
        String rookieToken = 회원가입을_하고(ROOKIE.getMember());
        String leoToken = 회원가입을_하고(LEO.getMember());
        쿠폰_생성을_요청하고(rookieToken, COFFEE_쿠폰_생성_요청(List.of(2L)));

        ExtractableResponse<Response> extract = 예약을_신청한다(
            leoToken, new ReservationCreateRequest(1L, LocalDate.now(), "예약할 때 보내는 메시지"));

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
        예약을_신청하고(leoToken, new ReservationCreateRequest(1L, LocalDate.now(), "예약할 때 보내는 메시지"));

        ExtractableResponse<Response> extract = 예약을_변경한다(
            1L, rookieToken, new ReservationChangeRequest("ACCEPT", "예약 상태를 변경할 때 보내는 메시지"));

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    static ExtractableResponse<Response> 예약을_변경한다(Long id, String token, Object data) {
        return invokePutWithToken("/api/reservations/" + id, token, data);
    }

    static void 예약을_신청하고(String token, ReservationCreateRequest request) {
        invokePostWithToken("/api/reservations", token, request);
    }

    static ExtractableResponse<Response> 예약을_신청한다(String token, Object data) {
        return invokePostWithToken("/api/reservations", token, data);
    }
}
