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

import com.woowacourse.kkogkkog.application.dto.CouponTemplateResponse;
import com.woowacourse.kkogkkog.application.dto.CouponTemplatesResponse;
import com.woowacourse.kkogkkog.domain.CouponType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

public class CouponTemplateControllerTest extends Documentation {

    @Test
    void 쿠폰_템플릿_전체_조회를_요청한다() throws Exception {
        CouponTemplatesResponse couponTemplatesResponse = new CouponTemplatesResponse(List.of(
                new CouponTemplateResponse(1L, "한턱쏘는", "#352446", CouponType.COFFEE.name()),
                new CouponTemplateResponse(2L, "한턱쏘는", "#352446", CouponType.MEAL.name())
        ));
        given(couponTemplateService.findAll()).willReturn(couponTemplatesResponse);

        // when
        ResultActions perform = mockMvc.perform(get("/api/templates"));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(
                        couponTemplatesResponse
                )));

        // docs
        perform
                .andDo(print())
                .andDo(document("coupon-template-showAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("쿠폰 템플릿 ID"),
                                fieldWithPath("data.[].modifier").type(JsonFieldType.STRING).description("수식어"),
                                fieldWithPath("data.[].backgroundColor").type(JsonFieldType.STRING).description("쿠폰 배경 색상"),
                                fieldWithPath("data.[].couponType").type(JsonFieldType.STRING).description("쿠폰 타입")
                        ))
                );
    }
}
