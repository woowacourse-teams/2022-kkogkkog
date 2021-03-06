package com.woowacourse.kkogkkog.documentation;

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

import com.woowacourse.kkogkkog.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.domain.CouponEvent;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.CouponType;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateRequest;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateResponse;
import com.woowacourse.kkogkkog.presentation.dto.CouponEventRequest;
import com.woowacourse.kkogkkog.presentation.dto.CouponsResponse;
import com.woowacourse.kkogkkog.presentation.dto.MyCouponsResponse;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
class CouponControllerTest extends Documentation {

    private static final String BEARER_TOKEN = "Bearer asdfghjkla.qwertyuuio.zxcvbnmzcv";
    private static final String BACKGROUND_COLOR = "#123456";
    private static final String MODIFIER = "????????????";
    private static final String MESSAGE = "?????? ?????????";
    private static final CouponType COUPON_TYPE = CouponType.COFFEE;
    private static final Member JEONG = new Member(1L, "jeong@gmail.com", "password1234!", "???");
    private static final Member LEO = new Member(2L, "leo@gmail.com", "password1234!", "??????");
    private static final Member ARTHUR = new Member(3L, "arthur@gmail.com", "password1234!", "??????");

    @Test
    void ??????_?????????_????????????() throws Exception {
        // given
        CouponCreateRequest couponCreateRequest = new CouponCreateRequest(List.of(2L, 3L), BACKGROUND_COLOR, MODIFIER,
                MESSAGE, COUPON_TYPE.name());
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.save(any())).willReturn(List.of(
                toCouponResponse(1L, JEONG, LEO), toCouponResponse(2L, JEONG, ARTHUR)));

        // when
        ResultActions perform = mockMvc.perform(post("/api/coupons")
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(couponCreateRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(new CouponCreateResponse(List.of(
                        toCouponResponse(1L, JEONG, LEO), toCouponResponse(2L, JEONG, ARTHUR))))));

        // docs
        perform
                .andDo(print())
                .andDo(document("coupon-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("??????")
                        ),
                        requestFields(
                                fieldWithPath("receivers").type(JsonFieldType.ARRAY).description("????????? ?????? ???????????? ID"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("modifier").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("couponType").type(JsonFieldType.STRING).description("?????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("?????? ID"),
                                fieldWithPath("data.[].sender.id").type(JsonFieldType.NUMBER).description("????????? ?????? ????????? ID"),
                                fieldWithPath("data.[].sender.nickname").type(JsonFieldType.STRING).description("????????? ?????? ????????? ?????????"),
                                fieldWithPath("data.[].sender.email").type(JsonFieldType.STRING).description("????????? ?????? ????????? ?????????"),
                                fieldWithPath("data.[].receiver.id").type(JsonFieldType.NUMBER).description("????????? ?????? ????????? ID"),
                                fieldWithPath("data.[].receiver.nickname").type(JsonFieldType.STRING).description("????????? ?????? ????????? ?????????"),
                                fieldWithPath("data.[].receiver.email").type(JsonFieldType.STRING).description("????????? ?????? ????????? ?????????"),
                                fieldWithPath("data.[].backgroundColor").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("data.[].modifier").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.[].message").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.[].couponType").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.[].couponStatus").type(JsonFieldType.STRING).description("?????? ??????")
                        ))
                );
    }

    @Test
    void ??????_??????_?????????_????????????() throws Exception {
        // given
        CouponResponse couponResponse = toCouponResponse(1L, JEONG, LEO);
        given(couponService.findById(any())).willReturn(couponResponse);

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
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ID"),
                                fieldWithPath("sender.id").type(JsonFieldType.NUMBER).description("?????? ?????? ID"),
                                fieldWithPath("sender.nickname").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("sender.email").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                                fieldWithPath("receiver.id").type(JsonFieldType.NUMBER).description("?????? ?????? ID"),
                                fieldWithPath("receiver.nickname").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("receiver.email").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                                fieldWithPath("backgroundColor").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("modifier").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("couponType").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("couponStatus").type(JsonFieldType.STRING).description("?????? ??????")
                        ))
                );
    }

    @Test
    void ????????????_????????????_??????_??????_?????????_????????????() throws Exception {
        // given
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.findAllBySender(any())).willReturn(List.of(
                toCouponResponse(1L, JEONG, LEO), toCouponResponse(2L, JEONG, ARTHUR)));
        given(couponService.findAllByReceiver(any())).willReturn(List.of(
                toCouponResponse(3L, LEO, JEONG), toCouponResponse(4L, ARTHUR, JEONG)));

        // when
        ResultActions perform = mockMvc.perform(get("/api/coupons")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        MyCouponsResponse myCouponsResponse = new MyCouponsResponse(new CouponsResponse(
                List.of(toCouponResponse(3L, LEO, JEONG), toCouponResponse(4L, ARTHUR, JEONG)),
                List.of(toCouponResponse(1L, JEONG, LEO), toCouponResponse(2L, JEONG, ARTHUR))));

        perform.andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(myCouponsResponse)));

        // docs
        perform
                .andDo(print())
                .andDo(document("coupon-showAll",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("??????")
                        ),
                        responseFields(
                                fieldWithPath("data.received.[].id").type(JsonFieldType.NUMBER).description("?????? ?????? ID"),
                                fieldWithPath("data.received.[].sender.id").type(JsonFieldType.NUMBER).description("?????? ?????? ID"),
                                fieldWithPath("data.received.[].sender.nickname").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("data.received.[].sender.email").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                                fieldWithPath("data.received.[].receiver.id").type(JsonFieldType.NUMBER).description("?????? ?????? ID"),
                                fieldWithPath("data.received.[].receiver.nickname").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("data.received.[].receiver.email").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                                fieldWithPath("data.received.[].backgroundColor").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("data.received.[].modifier").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.received.[].message").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.received.[].couponType").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.received.[].couponStatus").type(JsonFieldType.STRING).description("?????? ??????"),

                                fieldWithPath("data.sent.[].id").type(JsonFieldType.NUMBER).description("?????? ?????? ID"),
                                fieldWithPath("data.sent.[].sender.id").type(JsonFieldType.NUMBER).description("?????? ?????? ID"),
                                fieldWithPath("data.sent.[].sender.nickname").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("data.sent.[].sender.email").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                                fieldWithPath("data.sent.[].receiver.id").type(JsonFieldType.NUMBER).description("?????? ?????? ID"),
                                fieldWithPath("data.sent.[].receiver.nickname").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("data.sent.[].receiver.email").type(JsonFieldType.STRING).description("?????? ?????? ?????????"),
                                fieldWithPath("data.sent.[].backgroundColor").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                fieldWithPath("data.sent.[].modifier").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.sent.[].message").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.sent.[].couponType").type(JsonFieldType.STRING).description("?????? ??????"),
                                fieldWithPath("data.sent.[].couponStatus").type(JsonFieldType.STRING).description("?????? ??????")
                        ))
                );
    }

    @Test
    void ??????_??????_?????????_????????????() throws Exception {
        // given
        CouponEventRequest couponEventRequest = new CouponEventRequest(CouponEvent.REQUEST.name());
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");

        // when
        ResultActions perform = mockMvc.perform(post("/api/coupons/{couponId}/event", 1L)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .content(objectMapper.writeValueAsString(couponEventRequest))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform
                .andDo(print())
                .andDo(document("coupon-action",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("??????")
                        ),
                        requestFields(
                                fieldWithPath("couponEvent").type(JsonFieldType.STRING).description("?????? ?????????")
                        ))
                );
    }

    private CouponResponse toCouponResponse(Long couponId, Member sender, Member receiver) {
        CouponMemberResponse senderResponse = CouponMemberResponse.of(sender);
        CouponMemberResponse receiverResponse = CouponMemberResponse.of(receiver);
        return new CouponResponse(couponId, senderResponse, receiverResponse,
                BACKGROUND_COLOR, MODIFIER, MESSAGE, COUPON_TYPE.name(), CouponStatus.READY.name());
    }
}
