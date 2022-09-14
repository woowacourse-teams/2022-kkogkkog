package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입을_하고;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeGet;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokeGetWithToken;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokePostWithToken;
import static com.woowacourse.kkogkkog.acceptance.support.AcceptanceContext.invokePutWithToken;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.쿠폰_이벤트_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.acceptance.support.AcceptanceTest;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class CouponAcceptanceTest extends AcceptanceTest {

    @Test
    void 쿠폰_생성을_할_수_있다() {
        회원가입을_하고(LEO.getMember());
        회원가입을_하고(AUTHOR.getMember());
        String senderToken = 회원가입을_하고(JEONG.getMember());

        ExtractableResponse<Response> extract = 쿠폰_생성을_요청한다(
            senderToken, COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));

        CouponsResponse actual = extract.as(CouponsResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(actual.getData()).hasSize(2)
        );
    }

    @Test
    void 회원의_보낸쿠폰을_조회를_할_수_있다() {
        회원가입을_하고(LEO.getMember());
        회원가입을_하고(AUTHOR.getMember());
        String senderToken = 회원가입을_하고(JEONG.getMember());
        쿠폰_생성을_요청하고(senderToken, COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));

        ExtractableResponse<Response> extract = 회원의_보낸쿠폰_목록들을_조회한다(senderToken);

        CouponsResponse actual = extract.as(CouponsResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(actual.getData()).hasSize(2)
        );
    }

    @Test
    void 회원의_받은쿠폰_조회를_할_수_있다() {
        회원가입을_하고(LEO.getMember());
        회원가입을_하고(AUTHOR.getMember());
        String senderToken = 회원가입을_하고(JEONG.getMember());
        쿠폰_생성을_요청하고(senderToken, COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));

        ExtractableResponse<Response> extract = 회원의_받은쿠폰_목록들을_조회한다(senderToken);

        CouponsResponse actual = extract.as(CouponsResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(actual.getData()).hasSize(0)
        );
    }

    @Test
    void 단일_쿠폰을_상세_조회할_수_있다() {
        회원가입을_하고(LEO.getMember());
        회원가입을_하고(AUTHOR.getMember());
        String senderToken = 회원가입을_하고(JEONG.getMember());
        ExtractableResponse<Response> extractableResponse = 쿠폰_생성을_요청한다(senderToken,
            COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));

        CouponsResponse couponsResponse = extractableResponse.as(CouponsResponse.class);
        ExtractableResponse<Response> extract = 회원의_단일쿠폰_상세정보를_조회한다(
            couponsResponse.getData().get(0).getId());

        CouponDetailResponse couponDetailResponse = extract.as(CouponDetailResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(couponDetailResponse.getCouponStatus()).isEqualTo(
                CouponStatus.READY.name()),
            () -> assertThat(couponDetailResponse.getCouponHistories()).hasSize(1)
        );
    }

    @Test
    void 쿠폰에_대해서_받은사람이_사용신청을_할_수_있다() {
        String firstReceiverToken = 회원가입을_하고(LEO.getMember());
        String secondReceiverToken = 회원가입을_하고(AUTHOR.getMember());
        String senderToken = 회원가입을_하고(JEONG.getMember());
        CouponsResponse couponsResponse = 쿠폰_생성을_요청하고(
            senderToken, COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));
        Long couponId = couponsResponse.getData().get(1).getId();

        ExtractableResponse<Response> extract = 쿠폰_이벤트_요청을_한다(secondReceiverToken, couponId,
            쿠폰_이벤트_요청(
                "REQUEST",
                createMeetingTime(2022, 1, 1),
                "쿠폰 미팅을 위한 메시지"));

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    @Test
    void 쿠폰에_대해서_받은사람이_사용신청을_하고_보낸사람이_사용거절을_할_수_있다() {
        String firstReceiverToken = 회원가입을_하고(LEO.getMember());
        String secondReceiverToken = 회원가입을_하고(AUTHOR.getMember());
        String senderToken = 회원가입을_하고(JEONG.getMember());
        CouponsResponse couponsResponse = 쿠폰_생성을_요청하고(senderToken,
            COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));
        Long couponId = couponsResponse.getData().get(1).getId();
        쿠폰_이벤트_요청을_한다(secondReceiverToken, couponId, 쿠폰_이벤트_요청(
            "REQUEST",
            createMeetingTime(2022, 1, 1),
            "쿠폰 미팅을 위한 메시지"));

        ExtractableResponse<Response> extract = 쿠폰_이벤트_요청을_한다(senderToken, couponId, 쿠폰_이벤트_요청(
            "DECLINE",
            null,
            null));

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    @Test
    void 쿠폰에_대해서_받은사람이_사용신청을_하고_받은사람이_사용취소를_할_수_있다() {
        String firstReceiverToken = 회원가입을_하고(LEO.getMember());
        String secondReceiverToken = 회원가입을_하고(AUTHOR.getMember());
        String senderToken = 회원가입을_하고(JEONG.getMember());
        CouponsResponse couponsResponse = 쿠폰_생성을_요청하고(senderToken,
            COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));
        Long couponId = couponsResponse.getData().get(1).getId();
        쿠폰_이벤트_요청을_한다(secondReceiverToken, couponId, 쿠폰_이벤트_요청(
            "REQUEST",
            createMeetingTime(2022, 1, 1),
            "쿠폰 미팅을 위한 메시지"));

        ExtractableResponse<Response> extract = 쿠폰_이벤트_요청을_한다(secondReceiverToken, couponId,
            쿠폰_이벤트_요청(
                "CANCEL",
                null,
                null));

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    @Test
    void 쿠폰에_대해서_받은사람이_사용신청을_하고_보낸사람이_미팅수락을_할_수_있다() {
        String firstReceiverToken = 회원가입을_하고(LEO.getMember());
        String secondReceiverToken = 회원가입을_하고(AUTHOR.getMember());
        String senderToken = 회원가입을_하고(JEONG.getMember());
        CouponsResponse couponsResponse = 쿠폰_생성을_요청하고(senderToken,
            COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));
        Long couponId = couponsResponse.getData().get(1).getId();
        쿠폰_이벤트_요청을_한다(secondReceiverToken, couponId, 쿠폰_이벤트_요청(
            "REQUEST",
            createMeetingTime(2022, 1, 1),
            "쿠폰 미팅을 위한 메시지"));

        ExtractableResponse<Response> extract = 쿠폰_이벤트_요청을_한다(senderToken, couponId,
            쿠폰_이벤트_요청(
                "ACCEPT",
                null,
                null));

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    @Test
    void 쿠폰에_대해서_받은사람이_사용신청을_하고_받은사람이_미팅취소_할_수_있다() {
        String firstReceiverToken = 회원가입을_하고(LEO.getMember());
        String secondReceiverToken = 회원가입을_하고(AUTHOR.getMember());
        String senderToken = 회원가입을_하고(JEONG.getMember());
        CouponsResponse couponsResponse = 쿠폰_생성을_요청하고(senderToken,
            COFFEE_쿠폰_생성_요청(List.of(1L, 2L)));
        Long couponId = couponsResponse.getData().get(1).getId();
        쿠폰_이벤트_요청을_한다(secondReceiverToken, couponId, 쿠폰_이벤트_요청(
            "REQUEST",
            createMeetingTime(2022, 1, 1),
            "쿠폰 미팅을 위한 메시지"));
        쿠폰_이벤트_요청을_한다(senderToken, couponId, 쿠폰_이벤트_요청(
            "ACCEPT",
            null,
            null));

        ExtractableResponse<Response> extract = 쿠폰_이벤트_요청을_한다(senderToken, couponId, 쿠폰_이벤트_요청(
            "CANCEL",
            null,
            null));

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        );
    }

    static ExtractableResponse<Response> 쿠폰_생성을_요청한다(String token, Object data) {
        return invokePostWithToken("/api/coupons", token, data);
    }

    static ExtractableResponse<Response> 회원의_보낸쿠폰_목록들을_조회한다(String token) {
        return invokeGetWithToken("/api/coupons/send", token);
    }

    static ExtractableResponse<Response> 회원의_받은쿠폰_목록들을_조회한다(String token) {
        return invokeGetWithToken("/api/coupons/received", token);
    }

    static ExtractableResponse<Response> 회원의_단일쿠폰_상세정보를_조회한다(Long couponId) {
        return invokeGet("/api/coupons/" + couponId);
    }

    static ExtractableResponse<Response> 쿠폰_이벤트_요청을_한다(String token, Long couponId, Object data) {
        return invokePutWithToken("/api/coupons/" + couponId + "/event", token, data);
    }

    static CouponsResponse 쿠폰_생성을_요청하고(String token, Object data) {
        ExtractableResponse<Response> response = invokePostWithToken("/api/coupons", token, data);
        return response.as(CouponsResponse.class);
    }

    private LocalDateTime createMeetingTime(int year, int month, int day) {
        return LocalDateTime.of(year, month, day, 0, 0, 0);
    }
}
