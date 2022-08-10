package com.woowacourse.kkogkkog.core.reservation.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.common.fixture.dto.ReservationDtoFixture.예약_생성_요청;
import static com.woowacourse.kkogkkog.core.coupon.acceptance.CouponAcceptanceTest.쿠폰_생성을_요청하고;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

import com.woowacourse.kkogkkog.acceptance.AcceptanceTest;
import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ReservationAcceptanceTest extends AcceptanceTest {

    public static ExtractableResponse<Response> 예약을_신청한디(String token, Object data) {
        return invokePostWithToken("/api/reservations", token, data);
    }

    @Test
    void 예약을_신청할_수_있다() {
        String rookieToken = 로그인을_하고(MemberResponse.of(ROOKIE.getMember()));
        String leoToken = 로그인을_하고(MemberResponse.of(LEO.getMember()));

        쿠폰_생성을_요청하고(rookieToken, COFFEE_쿠폰_생성_요청(List.of(2L)));

        ExtractableResponse<Response> extract = 예약을_신청한디(
            leoToken, 예약_생성_요청(1L, LocalDate.now()));

        assertAll(
            () -> assertThat(extract.header("Location").split("/")[3]).isEqualTo(1),
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        );
    }

    // 임시로 다음위치에 이동함 (리팩토링 대상)
    private String 로그인을_하고(MemberResponse memberResponse) {
        given(slackClient.getUserInfoByCode("CODE"))
            .willReturn(
                new SlackUserInfo(
                    memberResponse.getUserId(),
                    memberResponse.getWorkspaceId(),
                    memberResponse.getNickname(),
                    memberResponse.getEmail(),
                    memberResponse.getImageUrl()));

        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .queryParam("code", "CODE")
            .get("/api/login/token")
            .then().log().all()
            .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        return extract.as(TokenResponse.class).getAccessToken();
    }
}
