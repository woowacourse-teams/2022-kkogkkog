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

import com.woowacourse.kkogkkog.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.CouponType;
import com.woowacourse.kkogkkog.domain.Member;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
class CouponControllerTest extends Documentation {

    private static final String BACKGROUND_COLOR = "#123456";
    private static final String MODIFIER = "한턱내는";
    private static final String MESSAGE = "추가 메세지";
    private static final CouponType COUPON_TYPE = CouponType.COFFEE;
    private static final Member JEONG = new Member(1L, "jeong@gmail.com", "password1234!", "정");
    private static final Member LEO = new Member(2L, "leo@gmail.com", "password1234!", "레오");

    @Test
    void 단일_쿠폰_조회를_요청한다() throws Exception {
        // given
        CouponSaveRequest couponSaveRequest = toCouponSaveRequest(JEONG, List.of(LEO));
        CouponResponse couponResponse = toCouponResponse(1L, JEONG, LEO);
        given(couponService.save(couponSaveRequest)).willReturn(List.of(couponResponse));
        given(couponService.findById(1L)).willReturn(couponResponse);

        // when
        ResultActions perform = mockMvc.perform(get("/api/coupons/{couponId}", 1L));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(couponResponse)));

        // docs
        perform
                .andDo(print())
                .andDo(document("coupon-show",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("쿠폰 ID"),
                                fieldWithPath("sender.id").type(JsonFieldType.NUMBER).description("보낸 사람 ID"),
                                fieldWithPath("sender.nickname").type(JsonFieldType.STRING).description("보낸 사람 이름"),
                                fieldWithPath("sender.email").type(JsonFieldType.STRING).description("보낸 사람 이메일"),
                                fieldWithPath("receiver.id").type(JsonFieldType.NUMBER).description("받는 사람 ID"),
                                fieldWithPath("receiver.nickname").type(JsonFieldType.STRING).description("받는 사람 이름"),
                                fieldWithPath("receiver.email").type(JsonFieldType.STRING).description("받는 사람 이메일"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("카드 배경 색상"),
                                fieldWithPath("modifier").type(JsonFieldType.STRING).description("수식어"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("추가 메시지"),
                                fieldWithPath("couponType").type(JsonFieldType.STRING).description("쿠폰 타입"),
                                fieldWithPath("couponStatus").type(JsonFieldType.STRING).description("쿠폰 상태")
                        ))
                );
    }

    private CouponSaveRequest toCouponSaveRequest(Member sender, List<Member> receivers) {
        Long senderId = sender.getId();
        List<Long> receiverIds = receivers.stream()
                .map(Member::getId)
                .collect(Collectors.toList());
        return new CouponSaveRequest(senderId, receiverIds, BACKGROUND_COLOR, MODIFIER, MESSAGE, COUPON_TYPE.name());
    }

    private CouponResponse toCouponResponse(Long couponId, Member sender, Member receiver) {
        CouponMemberResponse senderResponse = CouponMemberResponse.of(sender);
        CouponMemberResponse receiverResponse = CouponMemberResponse.of(receiver);
        return new CouponResponse(couponId, senderResponse, receiverResponse,
                BACKGROUND_COLOR, MODIFIER, MESSAGE, COUPON_TYPE.name(), CouponStatus.READY.name());
    }
}
