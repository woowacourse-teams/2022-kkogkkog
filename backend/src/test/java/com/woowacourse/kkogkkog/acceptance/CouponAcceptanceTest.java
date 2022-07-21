package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.로그인에_성공한다;
import static com.woowacourse.kkogkkog.acceptance.MemberAcceptanceTest.회원_가입에_성공한다;
import static com.woowacourse.kkogkkog.fixture.MemberFixture.NON_EXISTING_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.domain.CouponEvent;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateRequest;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateResponse;
import com.woowacourse.kkogkkog.presentation.dto.CouponEventRequest;
import com.woowacourse.kkogkkog.presentation.dto.CouponsResponse;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import com.woowacourse.kkogkkog.presentation.dto.MyCouponsResponse;
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

    private static final Member JEONG = new Member(1L, "jeong@gmail.com", "password1234!", "정");
    private static final Member LEO = new Member(2L, "leo@gmail.com", "password1234!", "레오");
    private static final Member ARTHUR = new Member(3L, "arthur@gmail.com", "password1234!", "아서");
    private static final Member ROOKIE = new Member(4L, "rookie@gmail.com", "password1234!", "루키");

    private static final String BACKGROUND_COLOR = "#123456";
    private static final String MODIFIER = "한턱내는";
    private static final String MESSAGE = "추가 메세지";
    private static final String COUPON_TYPE = "COFFEE";

    @DisplayName("GET /api/coupons - 나의 쿠폰 목록 조회")
    @Nested
    class ShowAllTest {

        @Test
        void 로그인된_사용자는_받은_쿠폰과_보낸_쿠폰을_전부_조회할_수_있다() {
            회원_가입에_성공한다(toMemberCreateRequest(JEONG));
            회원_가입에_성공한다(toMemberCreateRequest(LEO));
            회원_가입에_성공한다(toMemberCreateRequest(ARTHUR));
            String jeongAccessToken = 로그인에_성공한다(toTokenRequest(JEONG)).getAccessToken();
            String leoAccessToken = 로그인에_성공한다(toTokenRequest(LEO)).getAccessToken();
            List<CouponResponse> sentCoupons = 쿠폰_발급에_성공한다(jeongAccessToken, List.of(LEO, ARTHUR)).getData();
            List<CouponResponse> receivedCoupons = 쿠폰_발급에_성공한다(leoAccessToken, List.of(JEONG)).getData();

            MyCouponsResponse actual = 쿠폰_전체_조회에_성공한다(jeongAccessToken);
            MyCouponsResponse expected = new MyCouponsResponse(new CouponsResponse(receivedCoupons, sentCoupons));

            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(expected);
        }

        @Test
        void 로그인_하지_않은_사용자는_자신의_쿠폰을_조회할_수_없다() {
            ExtractableResponse<Response> response = 쿠폰_전체_조회를_요청한다("wrong_token");

            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
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
    }

    @DisplayName("POST /api/coupons - 쿠폰 발급")
    @Nested
    class CreateTest {

        @Test
        void 로그인된_사용자는_복수의_사용자에게_쿠폰을_발급할_수_있다() {
            회원_가입에_성공한다(toMemberCreateRequest(JEONG));
            회원_가입에_성공한다(toMemberCreateRequest(LEO));
            회원_가입에_성공한다(toMemberCreateRequest(ARTHUR));
            회원_가입에_성공한다(toMemberCreateRequest(ROOKIE));
            String jeongAccessToken = 로그인에_성공한다(toTokenRequest(JEONG)).getAccessToken();

            CouponCreateResponse actual = 쿠폰_발급에_성공한다(jeongAccessToken, List.of(LEO, ARTHUR, ROOKIE));
            CouponCreateResponse expected = new CouponCreateResponse(List.of(
                    toCouponResponse(1L, JEONG, LEO),
                    toCouponResponse(2L, JEONG, ARTHUR),
                    toCouponResponse(3L, JEONG, ROOKIE)));

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
            회원_가입에_성공한다(toMemberCreateRequest(JEONG));
            String accessToken = 로그인에_성공한다(toTokenRequest(JEONG)).getAccessToken();

            ExtractableResponse<Response> response = 쿠폰_발급을_요청한다(accessToken, List.of(JEONG));

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
            회원_가입에_성공한다(toMemberCreateRequest(JEONG));
            회원_가입에_성공한다(toMemberCreateRequest(LEO));
            String accessToken = 로그인에_성공한다(toTokenRequest(JEONG)).getAccessToken();

            CouponResponse expected = 쿠폰_발급에_성공한다(accessToken, List.of(LEO)).getData().get(0);
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

    @DisplayName("POST /api/coupons/{couponId}/event - 쿠폰 이벤트 발생")
    @Nested
    class ActionTest {

        @Test
        void 로그인된_사용자는_받은_쿠폰에_대해_사용_요청을_보낼_수_있다() {
            회원_가입에_성공한다(toMemberCreateRequest(JEONG));
            회원_가입에_성공한다(toMemberCreateRequest(LEO));
            String jeongAccessToken = 로그인에_성공한다(toTokenRequest(JEONG)).getAccessToken();
            String leoAccessToken = 로그인에_성공한다(toTokenRequest(LEO)).getAccessToken();
            쿠폰_발급에_성공한다(jeongAccessToken, List.of(LEO));

            ExtractableResponse<Response> response = 쿠폰_상태_변경을_요청한다(leoAccessToken, 1L, CouponEvent.REQUEST.name());

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 존재하지_않는_이벤트를_보낼_수_없다() {
            회원_가입에_성공한다(toMemberCreateRequest(JEONG));
            회원_가입에_성공한다(toMemberCreateRequest(LEO));
            String jeongAccessToken = 로그인에_성공한다(toTokenRequest(JEONG)).getAccessToken();
            String leoAccessToken = 로그인에_성공한다(toTokenRequest(LEO)).getAccessToken();
            쿠폰_발급에_성공한다(jeongAccessToken, List.of(LEO));

            ExtractableResponse<Response> response = 쿠폰_상태_변경을_요청한다(leoAccessToken, 1L, "존재하지_않는_이벤트");

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void 쿠폰을_보낸_사람은_사용_요청을_취소_할_수_있다() {
            회원_가입에_성공한다(toMemberCreateRequest(JEONG));
            회원_가입에_성공한다(toMemberCreateRequest(LEO));
            String jeongAccessToken = 로그인에_성공한다(toTokenRequest(JEONG)).getAccessToken();
            String leoAccessToken = 로그인에_성공한다(toTokenRequest(LEO)).getAccessToken();
            쿠폰_발급에_성공한다(jeongAccessToken, List.of(LEO));

            쿠폰_상태_변경을_요청한다(leoAccessToken, 1L, CouponEvent.REQUEST.name());
            ExtractableResponse<Response> response = 쿠폰_상태_변경을_요청한다(leoAccessToken, 1L, CouponEvent.CANCEL.name());

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

        @Test
        void 사용_요청_상태가_아닌_쿠폰은_요청을_취소할_수_없다() {
            회원_가입에_성공한다(toMemberCreateRequest(JEONG));
            회원_가입에_성공한다(toMemberCreateRequest(LEO));
            String jeongAccessToken = 로그인에_성공한다(toTokenRequest(JEONG)).getAccessToken();
            String leoAccessToken = 로그인에_성공한다(toTokenRequest(LEO)).getAccessToken();
            쿠폰_발급에_성공한다(jeongAccessToken, List.of(LEO));

            ExtractableResponse<Response> response = 쿠폰_상태_변경을_요청한다(leoAccessToken, 1L, CouponEvent.CANCEL.name());

            assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }

        private ExtractableResponse<Response> 쿠폰_상태_변경을_요청한다(String accessToken,
                                                             Long couponId,
                                                             String couponEvent) {
            return RestAssured.given().log().all()
                    .auth().oauth2(accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new CouponEventRequest(couponEvent))
                    .when()
                    .post("/api/coupons/{couponId}/event", couponId)
                    .then().log().all()
                    .extract();
        }
    }

    public static CouponCreateResponse 쿠폰_발급에_성공한다(String accessToken, List<Member> receivers) {
        ExtractableResponse<Response> response = 쿠폰_발급을_요청한다(accessToken, receivers);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        return response.as(CouponCreateResponse.class);
    }

    public static ExtractableResponse<Response> 쿠폰_발급을_요청한다(String accessToken, List<Member> receivers) {
        List<Long> receiverIds = receivers.stream()
                .map(Member::getId)
                .collect(Collectors.toList());

        return RestAssured.given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CouponCreateRequest(receiverIds, MODIFIER, MESSAGE, BACKGROUND_COLOR, COUPON_TYPE))
                .when()
                .post("/api/coupons")
                .then().log().all()
                .extract();
    }

    private MemberCreateRequest toMemberCreateRequest(Member member) {
        return new MemberCreateRequest(member.getEmail(), member.getPassword(), member.getNickname());
    }

    private TokenRequest toTokenRequest(Member member) {
        return new TokenRequest(member.getEmail(), member.getPassword());
    }

    private CouponResponse toCouponResponse(Long couponId, Member sender, Member receiver) {
        CouponMemberResponse senderResponse = CouponMemberResponse.of(sender);
        CouponMemberResponse receiverResponse = CouponMemberResponse.of(receiver);
        return new CouponResponse(couponId, senderResponse, receiverResponse,
                MODIFIER, MESSAGE, BACKGROUND_COLOR, COUPON_TYPE, CouponStatus.READY.name());
    }
}
