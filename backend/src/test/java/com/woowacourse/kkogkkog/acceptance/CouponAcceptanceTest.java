package com.woowacourse.kkogkkog.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.fixture.MemberFixture;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
        memberRepository.save(MemberFixture.ROOKIE);
        memberRepository.save(MemberFixture.ARTHUR);
        memberRepository.save(MemberFixture.JEONG);
        memberRepository.save(MemberFixture.LEO);
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

    public static CouponResponse 쿠폰_조회에_성공한다(Long couponId) {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
                .when()
                .get("/api/coupons/" + couponId)
                .then().log().all()
                .extract();

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        return extract.as(CouponResponse.class);
    }
}
