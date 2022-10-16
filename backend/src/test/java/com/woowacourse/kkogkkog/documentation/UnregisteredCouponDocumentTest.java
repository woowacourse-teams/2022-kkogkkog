package com.woowacourse.kkogkkog.documentation;

import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentResponse;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_응답;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.미등록_COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.미등록_COFFEE_쿠폰_응답;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.수령한_미등록_COFFEE_쿠폰_응답;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.쿠폰_코드_등록_요청;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.unregisteredcoupon.presentation.dto.UnregisteredCouponsResponse;
import com.woowacourse.kkogkkog.documentation.support.DocumentTest;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
class UnregisteredCouponDocumentTest extends DocumentTest {

    private final String BEARER_TOKEN = "Bearer {Access Token}";
    private static final String COUPON_CODE = "쿠폰코드";

    @Test
    void 미등록_쿠폰_생성_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(unregisteredCouponService.save(any())).willReturn(List.of(
            미등록_COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L))
        ));

        ResultActions perform = mockMvc.perform(
            post("/api/v2/coupons/unregistered")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .content(objectMapper.writeValueAsString(미등록_COFFEE_쿠폰_생성_요청(1)))
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isCreated())
            .andExpect(
                content().string(objectMapper.writeValueAsString(new UnregisteredCouponsResponse(
                    List.of(미등록_COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L)))))));

        perform
            .andDo(print())
            .andDo(document("unregistered-coupon-create",
                getDocumentRequest(),
                getDocumentResponse()));
    }


    @Test
    void 쿠폰_코드_등록_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(unregisteredCouponService.saveByCouponCode(any(), any())).willReturn(
            COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L)));

        String couponCode = "쿠폰코드";
        ResultActions perform = mockMvc.perform(
            post("/api/v2/coupons/code")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .content(objectMapper.writeValueAsString(쿠폰_코드_등록_요청(couponCode)))
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isCreated())
            .andExpect(
                content().string(objectMapper.writeValueAsString(
                    COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L)))));

        perform
            .andDo(print())
            .andDo(document("coupon-create-code",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 나의_미등록_쿠폰_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        Member sender = SENDER.getMember(1L);
        given(unregisteredCouponService.findAllBySender(any())).willReturn(
            List.of(미등록_COFFEE_쿠폰_응답(1L, sender), 미등록_COFFEE_쿠폰_응답(2L, sender)));

        ResultActions perform = mockMvc.perform(
            get("/api/v2/coupons/unregistered")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN));

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(new UnregisteredCouponsResponse(
                    List.of(미등록_COFFEE_쿠폰_응답(1L, sender),
                        미등록_COFFEE_쿠폰_응답(2L, sender))))));

        perform
            .andDo(print())
            .andDo(document("unregistered-coupon-showAll",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 나의_미등록_쿠폰_상태별_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        Member sender = SENDER.getMember(1L);
        Member receiver = RECEIVER.getMember(2L);
        given(unregisteredCouponService.findAllBySender(any(), any())).willReturn(
            List.of(수령한_미등록_COFFEE_쿠폰_응답(1L, 1L, sender, receiver),
                수령한_미등록_COFFEE_쿠폰_응답(2L, 2L, sender, receiver)));

        ResultActions perform = mockMvc.perform(
            get("/api/v2/coupons/unregistered/status")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .param("type", "REGISTERED"));

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(new UnregisteredCouponsResponse(
                    List.of(수령한_미등록_COFFEE_쿠폰_응답(1L, 1L, sender, receiver),
                        수령한_미등록_COFFEE_쿠폰_응답(2L, 2L, sender, receiver))))));

        perform
            .andDo(print())
            .andDo(document("unregistered-coupon-show-status",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 미등록_쿠폰_아이디_단일_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(unregisteredCouponService.findById(any(), any())).willReturn(
            미등록_COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L))
        );

        ResultActions perform = mockMvc.perform(
            get("/api/v2/coupons/unregistered/1")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN));

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(
                    미등록_COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L)))));

        perform
            .andDo(print())
            .andDo(document("unregistered-coupon-show-id",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 미등록_쿠폰_쿠폰코드_단일_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(unregisteredCouponService.findByCouponCode(any())).willReturn(
            미등록_COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), COUPON_CODE)
        );

        ResultActions perform = mockMvc.perform(
            get("/api/v2/coupons/unregistered/code")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .param("couponCode", COUPON_CODE));

            perform.andExpect(status().isOk())
                .andExpect(
                    content().string(objectMapper.writeValueAsString(
                        미등록_COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), COUPON_CODE))));

        perform
            .andDo(print())
            .andDo(document("unregistered-coupon-show-code",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 나의_미등록_쿠폰_삭제_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");

        ResultActions perform = mockMvc.perform(
            delete("/api/v2/coupons/unregistered/1")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN));

        perform.andExpect(status().isOk());

        perform
            .andDo(print())
            .andDo(document("unregistered-coupon-delete",
                getDocumentRequest(),
                getDocumentResponse()));
    }
}
