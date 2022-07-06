package com.woowacourse.kkogkkog.documentation;

import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponsResponse;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateRequest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
class CouponControllerTest extends Documentation {

    private static final String BACKGROUND_COLOR = "red";
    private static final String MODIFIER = "한턱내는";
    private static final String MESSAGE = "추가 메세지";
    private static final String COUPON_TYPE = "커피";

    @Test
    void 쿠폰_발급을_요청한다() throws Exception {
        // given
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(1L, 2L, BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE);
        given(couponService.save(couponCreateRequest)).willReturn(1L);

        // when
        ResultActions perform = mockMvc.perform(post("/api/coupons")
                .content(objectMapper.writeValueAsString(couponCreateRequest))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isCreated());

        // docs
        perform
                .andDo(print())
                .andDo(document("coupon-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("senderId").type(JsonFieldType.NUMBER).description("보낸 사람 ID"),
                                fieldWithPath("receiverId").type(JsonFieldType.NUMBER).description("받는 사람 ID"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("카드 배경 색상"),
                                fieldWithPath("modifier").type(JsonFieldType.STRING).description("수식어"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("추가 메시지"),
                                fieldWithPath("couponType").type(JsonFieldType.STRING).description("쿠폰 타입")
                        ))
                );
    }

    @Test
    void 단일_쿠폰_조회를_요청한다() throws Exception {
        // given
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(1L, 2L, BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE);
        CouponResponse couponResponse = new CouponResponse(1L, "루키", "아서", BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE, CouponStatus.READY.name());
        given(couponService.save(couponCreateRequest)).willReturn(1L);
        given(couponService.findById(1L)).willReturn(couponResponse);

        // when
        ResultActions perform = mockMvc.perform(get("/api/coupons/{couponId}", 1L));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        new CouponResponse(1L, "루키", "아서", BACKGROUND_COLOR, MODIFIER, MESSAGE,
                                COUPON_TYPE, CouponStatus.READY.name())
                )));

        // docs
        perform
                .andDo(print())
                .andDo(document("coupon-show",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("쿠폰 ID"),
                                fieldWithPath("senderName").type(JsonFieldType.STRING).description("보낸 사람 이름"),
                                fieldWithPath("receiverName").type(JsonFieldType.STRING).description("받는 사람 이름"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("카드 배경 색상"),
                                fieldWithPath("modifier").type(JsonFieldType.STRING).description("수식어"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("추가 메시지"),
                                fieldWithPath("couponType").type(JsonFieldType.STRING).description("쿠폰 타입"),
                                fieldWithPath("couponStatus").type(JsonFieldType.STRING).description("쿠폰 상태")
                        ))
                );
    }

    @Test
    void 전체_쿠폰_조회를_요청한다() throws Exception {
        // given
        CouponCreateRequest couponCreateRequest1 = new CouponCreateRequest(1L, 2L, BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE);
        CouponCreateRequest couponCreateRequest2 = new CouponCreateRequest(1L, 3L, BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE);
        CouponResponse couponResponse1 = new CouponResponse(1L, "루키", "아서", BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE, CouponStatus.READY.name());
        CouponResponse couponResponse2 = new CouponResponse(2L, "루키", "정", BACKGROUND_COLOR, MODIFIER, MESSAGE,
                COUPON_TYPE, CouponStatus.READY.name());

        given(couponService.save(couponCreateRequest1)).willReturn(1L);
        given(couponService.save(couponCreateRequest2)).willReturn(2L);
        given(couponService.findAll()).willReturn(new CouponsResponse(List.of(couponResponse1, couponResponse2)));

        // when
        ResultActions perform = mockMvc.perform(get("/api/coupons"));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        new CouponsResponse(List.of(couponResponse1, couponResponse2))
                )));

        // docs
        perform
                .andDo(print())
                .andDo(document("coupon-showAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("쿠폰 ID"),
                                fieldWithPath("data.[].senderName").type(JsonFieldType.STRING).description("보낸 사람 이름"),
                                fieldWithPath("data.[].receiverName").type(JsonFieldType.STRING).description("받는 사람 이름"),
                                fieldWithPath("data.[].backgroundColor").type(JsonFieldType.STRING).description("카드 배경 색상"),
                                fieldWithPath("data.[].modifier").type(JsonFieldType.STRING).description("수식어"),
                                fieldWithPath("data.[].message").type(JsonFieldType.STRING).description("추가 메시지"),
                                fieldWithPath("data.[].couponType").type(JsonFieldType.STRING).description("쿠폰 타입"),
                                fieldWithPath("data.[].couponStatus").type(JsonFieldType.STRING).description("쿠폰 상태")
                        ))
                );
    }
}
