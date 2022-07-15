package com.woowacourse.kkogkkog.acceptance;

import com.woowacourse.kkogkkog.application.CouponService;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.presentation.dto.CouponsResponse;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import com.woowacourse.kkogkkog.presentation.dto.MyCouponsResponse;
import com.woowacourse.kkogkkog.presentation.dto.TokenRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.로그인에_성공한다;
import static com.woowacourse.kkogkkog.acceptance.MemberAcceptanceTest.회원_가입에_성공한다;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
public class CouponAcceptance2Test extends AcceptanceTest {

    private static final Member JEONG = new Member(1L, "jeong@gmail.com", "password1234!", "정");
    private static final Member LEO = new Member(2L, "leo@gmail.com", "password1234!", "레오");
    private static final Member ARTHUR = new Member(3L, "arthur@gmail.com", "password1234!", "아서");


    private static final String BACKGROUND_COLOR = "#123456";
    private static final String MODIFIER = "한턱내는";
    private static final String MESSAGE = "추가 메세지";
    private static final String COUPON_TYPE = "COFFEE";

    @Autowired
    private CouponService couponService;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
    }

    @DisplayName("GET /api/coupons/")
    @Nested
    class ShowAllTest {

        @Test
        void 로그인된_사용자는_받은_쿠폰과_보낸_쿠폰을_전부_조회할_수_있다() {
            회원_가입에_성공한다(new MemberCreateRequest(JEONG.getEmail(), JEONG.getPassword(), JEONG.getNickname()));
            회원_가입에_성공한다(new MemberCreateRequest(LEO.getEmail(), LEO.getPassword(), LEO.getNickname()));
            회원_가입에_성공한다(new MemberCreateRequest(ARTHUR.getEmail(), ARTHUR.getPassword(), ARTHUR.getNickname()));
            TokenRequest tokenRequest = new TokenRequest(JEONG.getEmail(), JEONG.getPassword());
            String accessToken = 로그인에_성공한다(tokenRequest).getAccessToken();
            List<CouponResponse> sentCoupons = 쿠폰_발급에_성공한다(JEONG, List.of(LEO, ARTHUR));
            List<CouponResponse> receivedCoupons = 쿠폰_발급에_성공한다(LEO, List.of(JEONG));

            MyCouponsResponse actual = 쿠폰_전체_조회에_성공한다(accessToken);
            MyCouponsResponse expected = new MyCouponsResponse(new CouponsResponse(sentCoupons, receivedCoupons));

            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);

        }

        @Test
        void 로그인_하지_않은_사용자는_자신의_쿠폰을_조회할_수_없다() {
            ExtractableResponse<Response> response = 쿠폰_전체_조회를_요청한다("wrong_token");

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private MyCouponsResponse 쿠폰_전체_조회에_성공한다(String accessToken) {
        ExtractableResponse<Response> extract = 쿠폰_전체_조회를_요청한다(accessToken);
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        return extract.as(MyCouponsResponse.class);
    }

    private ExtractableResponse<Response> 쿠폰_전체_조회를_요청한다(String accessToken) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get("/api/coupons")
                .then().log().all()
                .extract();
    }

    // TODO: should be replaced with REST ASSURED after AUTHENTICATION
    public  List<CouponResponse> 쿠폰_발급에_성공한다(Member sender, List<Member> receivers) {
        return couponService.save(toCouponSaveRequest(sender, receivers));
    }

    private static ExtractableResponse<Response> 쿠폰_조회를_요청한다(Long couponId) {
        return RestAssured.given().log().all()
                .when()
                .get("/api/coupons/" + couponId)
                .then().log().all()
                .extract();
    }

    // TODO: POST /api/coupons API 구현 후 제거
    private CouponSaveRequest toCouponSaveRequest(Member sender, List<Member> receivers) {
        List<Long> receiverIds = receivers.stream()
                .map(Member::getId)
                .collect(Collectors.toList());
        return new CouponSaveRequest(sender.getId(), receiverIds, BACKGROUND_COLOR, MODIFIER, MESSAGE, COUPON_TYPE);
    }
}
