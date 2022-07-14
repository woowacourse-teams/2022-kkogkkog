package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.fixture.MemberFixture.ARTHUR;
import static com.woowacourse.kkogkkog.fixture.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.fixture.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.fixture.MemberFixture.ROOKIE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.application.CouponService2;
import com.woowacourse.kkogkkog.application.dto.CouponResponse2;
import com.woowacourse.kkogkkog.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class CouponAcceptanceTest extends AcceptanceTest {

    private static final String BACKGROUND_COLOR = "#123456";
    private static final String MODIFIER = "한턱내는";
    private static final String MESSAGE = "추가 메세지";
    private static final String COUPON_TYPE = "COFFEE";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponService2 couponService;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        memberRepository.save(ROOKIE);
        memberRepository.save(ARTHUR);
        memberRepository.save(JEONG);
        memberRepository.save(LEO);
    }

    @DisplayName("GET /api/coupons/{couponId}")
    @Nested
    class ShowCouponTest {

        @Test
        void 단일_쿠폰_조회를_할_수_있다() {
            CouponResponse2 expected = 쿠폰_발급에_성공한다(JEONG, List.of(LEO)).get(0);
            CouponResponse2 actual = 쿠폰_조회에_성공한다(expected.getId());

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        void 존재하지_않는_쿠폰을_조회할_수_없다() {
            ExtractableResponse<Response> response = 쿠폰_조회를_요청한다(99999L);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }
    }

    // TODO: should be replaced with REST ASSURED after AUTHENTICATION
    public  List<CouponResponse2> 쿠폰_발급에_성공한다(Member sender, List<Member> receivers) {
        return couponService.save(toCouponSaveRequest(sender, receivers));
    }

    public static CouponResponse2 쿠폰_조회에_성공한다(Long couponId) {
        ExtractableResponse<Response> extract = 쿠폰_조회를_요청한다(couponId);

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        return extract.as(CouponResponse2.class);
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
