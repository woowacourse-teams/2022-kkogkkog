package com.woowacourse.kkogkkog.documentation;

import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
class CouponControllerTest extends Documentation {

    private static final String BACKGROUND_COLOR = "red";
    private static final String MODIFIER = "한턱내는";
    private static final String MESSAGE = "추가 메세지";
    private static final String COUPON_TYPE = "커피";

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
}
