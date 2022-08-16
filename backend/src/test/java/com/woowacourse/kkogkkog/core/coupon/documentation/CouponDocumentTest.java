package com.woowacourse.kkogkkog.core.coupon.documentation;

import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_응답;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.쿠폰_생성_상세조회_응답;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.쿠폰과_예약정보_응답;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsCreateResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsReservationResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.MyCouponsReservationResponse;
import com.woowacourse.kkogkkog.documentation.Documentation;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
class CouponDocumentTest extends Documentation {

    private final String BEARER_TOKEN = "Bearer {Access Token}";

    @Test
    void 쿠폰_발급_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.save(any())).willReturn(List.of(
            COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L))));

        ResultActions perform = mockMvc.perform(
            post("/api/coupons")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .content(objectMapper.writeValueAsString(COFFEE_쿠폰_생성_요청(List.of(1L, 2L))))
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isCreated())
            .andExpect(
                content().string(objectMapper.writeValueAsString(new CouponsCreateResponse(
                    List.of(COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L)))))));


        perform
            .andDo(print())
            .andDo(document("coupon-create",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 나와_관련된_쿠폰_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.findAllByReceiver(any())).willReturn(List.of(
            쿠폰과_예약정보_응답(1L, 1L, 1L)));
        given(couponService.findAllBySender(any())).willReturn(List.of(
            쿠폰과_예약정보_응답(2L, 2L, 1L)));

        ResultActions perform = mockMvc.perform(
            get("/api/coupons")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN));

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(
                    new MyCouponsReservationResponse(
                        new CouponsReservationResponse(
                            List.of(쿠폰과_예약정보_응답(1L, 1L, 1L)),
                            List.of(쿠폰과_예약정보_응답(2L, 2L, 1L)))))));

        perform
            .andDo(print())
            .andDo(document("coupon-showAll-me",
                getDocumentRequest(),
                getDocumentResponse())
            );
    }

    @Test
    void 단일_쿠폰_상세_조회_API() throws Exception {
        given(couponService.find(any()))
            .willReturn(쿠폰_생성_상세조회_응답(1L, 1L, 2L));

        ResultActions perform = mockMvc.perform(
            get("/api/coupons/1"));

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(
                    쿠폰_생성_상세조회_응답(1L, 1L, 2L))));

        perform
            .andDo(print())
            .andDo(document("coupon-show",
                getDocumentRequest(),
                getDocumentResponse())
            );
    }
}
