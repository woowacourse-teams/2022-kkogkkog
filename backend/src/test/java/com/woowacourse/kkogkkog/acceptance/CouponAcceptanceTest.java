package com.woowacourse.kkogkkog.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponsResponse;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("쿠폰 관련")
public class CouponAcceptanceTest extends AcceptanceTest {

    private static final String BACKGROUND_COLOR = "red";
    private static final String MODIFIER = "한턱내는";
    private static final String MESSAGE = "추가 메세지";
    private static final String COUPON_TYPE = "커피";
    @Autowired
    private MemberRepository memberRepository;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        memberRepository.save(new Member(1L, "루키"));
        memberRepository.save(new Member(2L, "아서"));
        memberRepository.save(new Member(3L, "정"));
        memberRepository.save(new Member(4L, "레오"));
    }

    @Test
    void 쿠폰_발급을_할_수_있다() {
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(1L, 2L, BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE);
        쿠폰_발급에_성공한다(couponCreateRequest);
    }

    @Test
    void 보낸_사람과_받는_사람이_같은_경우_쿠폰_발급을_실패한다() {
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(1L, 1L, BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE);
        쿠폰_발급에_실패한다(couponCreateRequest);
    }

    @Test
    void 받는_사람이_존재하지_않는_경우_쿠폰_발급을_실패한다() {
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(1L, 999L, BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE);
        쿠폰_발급에_실패한다(couponCreateRequest);
    }

    @Test
    void 단일_쿠폰_조회를_할_수_있다() {
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(1L, 2L, BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE);
        Long couponId = 쿠폰_발급에_성공한다(couponCreateRequest);

        CouponResponse actual = 쿠폰_조회에_성공한다(couponId);
        CouponResponse expected = new CouponResponse(couponId, "루키", "아서", BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE, CouponStatus.READY.name());

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void 전체_쿠폰_조회를_할_수_있다() {
        CouponCreateRequest couponCreateRequest1 = new CouponCreateRequest(1L, 2L, BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE);
        CouponCreateRequest couponCreateRequest2 = new CouponCreateRequest(1L, 3L, BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE);

        Long couponId1 = 쿠폰_발급에_성공한다(couponCreateRequest1);
        Long couponId2 = 쿠폰_발급에_성공한다(couponCreateRequest2);

        CouponsResponse actual = 쿠폰_전체_조회에_성공한다();
        CouponsResponse expected = new CouponsResponse(List.of(
                new CouponResponse(couponId1, "루키", "아서", BACKGROUND_COLOR, MODIFIER, MESSAGE,
                        COUPON_TYPE, CouponStatus.READY.name()),
                new CouponResponse(couponId2, "루키", "정", BACKGROUND_COLOR, MODIFIER, MESSAGE,
                        COUPON_TYPE, CouponStatus.READY.name())
        ));

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static Long 쿠폰_발급에_성공한다(CouponCreateRequest couponCreateRequest) {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .body(couponCreateRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/coupons")
                .then().log().all()
                .extract();
        Long couponId = Long.valueOf(extract.header("Location").split("/")[3]);

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(couponId).isNotNull();
        return couponId;
    }

    public static void 쿠폰_발급에_실패한다(CouponCreateRequest couponCreateRequest) {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .body(couponCreateRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/coupons")
                .then().log().all()
                .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static CouponResponse 쿠폰_조회에_성공한다(Long couponId) {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .when()
                .get("/api/coupons/" + couponId)
                .then().log().all()
                .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        return extract.as(CouponResponse.class);
    }

    public static CouponsResponse 쿠폰_전체_조회에_성공한다() {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .when()
                .get("/api/coupons")
                .then().log().all()
                .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        return extract.as(CouponsResponse.class);
    }
}
