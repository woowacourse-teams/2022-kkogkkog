package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.로그인에_성공한다;
import static com.woowacourse.kkogkkog.acceptance.MemberAcceptanceTest.회원_가입에_성공한다;
import static com.woowacourse.kkogkkog.fixture.MemberFixture.NON_EXISTING_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponsResponse;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateRequest;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import com.woowacourse.kkogkkog.presentation.dto.TokenRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SuppressWarnings("NonAsciiCharacters")
public class CouponAcceptanceTest extends AcceptanceTest {

    public static Member LEO = new Member(1L, "leo@gmail.com", "password1234!", "레오");
    public static Member ROOKIE = new Member(2L, "rookie@gmail.com", "password1234!", "루키");
    public static Member ARTHUR = new Member(3L, "arthur@gmail.com", "password1234!", "아서");
    public static Member JEONG = new Member(4L, "jeong@gmail.com", "password1234!", "정");

    private static final String BACKGROUND_COLOR = "#123456";
    private static final String MODIFIER = "한턱내는";
    private static final String MESSAGE = "추가 메세지";
    private static final String COUPON_TYPE = "COFFEE";

    @DisplayName("POST /api/coupons - 쿠폰 발급")
    @Nested
    class CreateTest {

        @Test
        void 로그인된_사용자는_복수의_사용자에게_쿠폰을_발급할_수_있다() {
            Member leo = LEO;
            회원_가입에_성공한다(toMemberCreateRequest(leo));
            회원_가입에_성공한다(toMemberCreateRequest(ROOKIE));
            회원_가입에_성공한다(toMemberCreateRequest(ARTHUR));
            회원_가입에_성공한다(toMemberCreateRequest(JEONG));
            String accessToken = 로그인에_성공한다(new TokenRequest(leo.getEmail(), leo.getPassword())).getAccessToken();

            CouponsResponse actual = 쿠폰_발급에_성공한다(accessToken, List.of(ROOKIE, ARTHUR, JEONG));
            CouponsResponse expected = new CouponsResponse(List.of(toCouponResponse(1L, leo, ROOKIE),
                    toCouponResponse(2L, leo, ARTHUR), toCouponResponse(3L, leo, JEONG)));

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        void 로그인하지_않은_사용자는_쿠폰을_발급할_수_없다() {
            회원_가입에_성공한다(toMemberCreateRequest(LEO));
            회원_가입에_성공한다(toMemberCreateRequest(ROOKIE));

            ExtractableResponse<Response> response = 쿠폰_발급을_요청한다("wrong_token", List.of(ROOKIE));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }

        @Test
        void 자신에게_쿠폰을_발급할_수_없다() {
            Member leo = LEO;
            회원_가입에_성공한다(toMemberCreateRequest(leo));
            String accessToken = 로그인에_성공한다(new TokenRequest(leo.getEmail(), leo.getPassword())).getAccessToken();

            ExtractableResponse<Response> response = 쿠폰_발급을_요청한다(accessToken, List.of(leo));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 받는_사람이_존재하지_않는_경우_쿠폰을_발급할_수_없다() {
            Member leo = LEO;
            회원_가입에_성공한다(toMemberCreateRequest(leo));
            String accessToken = 로그인에_성공한다(new TokenRequest(leo.getEmail(), leo.getPassword())).getAccessToken();

            ExtractableResponse<Response> response = 쿠폰_발급을_요청한다(accessToken, List.of(NON_EXISTING_MEMBER));

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }
    }

    @DisplayName("GET /api/coupons/{couponId} - 쿠폰 단일 조회")
    @Nested
    class ShowTest {

        @Test
        void 생성된_쿠폰을_조회할_수_있다() {
            Member leo = LEO;
            회원_가입에_성공한다(toMemberCreateRequest(leo));
            회원_가입에_성공한다(toMemberCreateRequest(ROOKIE));
            String accessToken = 로그인에_성공한다(new TokenRequest(leo.getEmail(), leo.getPassword())).getAccessToken();

            CouponResponse expected = 쿠폰_발급에_성공한다(accessToken, List.of(ROOKIE)).getData().get(0);
            CouponResponse actual = 쿠폰_조회에_성공한다(expected.getId());

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        void 존재하지_않는_쿠폰을_조회할_수_없다() {
            ExtractableResponse<Response> response = 쿠폰_조회를_요청한다(99999L);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        }

        private CouponResponse 쿠폰_조회에_성공한다(Long couponId) {
            ExtractableResponse<Response> extract = 쿠폰_조회를_요청한다(couponId);

            assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
            return extract.as(CouponResponse.class);
        }

        private ExtractableResponse<Response> 쿠폰_조회를_요청한다(Long couponId) {
            return RestAssured.given().log().all()
                    .when()
                    .get("/api/coupons/" + couponId)
                    .then().log().all()
                    .extract();
        }
    }

    public static CouponsResponse 쿠폰_발급에_성공한다(String accessToken, List<Member> receivers) {
        ExtractableResponse<Response> response = 쿠폰_발급을_요청한다(accessToken, receivers);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        return response.as(CouponsResponse.class);
    }

    public static ExtractableResponse<Response> 쿠폰_발급을_요청한다(String accessToken, List<Member> receivers) {
        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(toCouponCreateRequest(receivers))
                .when()
                .post("/api/coupons")
                .then().log().all()
                .extract();
    }

    private static CouponCreateRequest toCouponCreateRequest(List<Member> receivers) {
        List<Long> receiverIds = receivers.stream()
                .map(Member::getId)
                .collect(Collectors.toList());
        return new CouponCreateRequest(receiverIds, MODIFIER, MESSAGE, BACKGROUND_COLOR, COUPON_TYPE);
    }

    private MemberCreateRequest toMemberCreateRequest(Member member) {
        return new MemberCreateRequest(member.getEmail(), member.getPassword(), member.getNickname());
    }

    private CouponResponse toCouponResponse(Long couponId, Member sender, Member receiver) {
        CouponMemberResponse senderResponse = CouponMemberResponse.of(sender);
        CouponMemberResponse receiverResponse = CouponMemberResponse.of(receiver);
        return new CouponResponse(couponId, senderResponse, receiverResponse,
                MODIFIER, MESSAGE, BACKGROUND_COLOR, COUPON_TYPE, CouponStatus.READY.name());
    }
}
