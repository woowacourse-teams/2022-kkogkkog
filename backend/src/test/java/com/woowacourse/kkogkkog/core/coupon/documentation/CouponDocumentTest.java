package com.woowacourse.kkogkkog.core.coupon.documentation;

import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_응답;
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
public class CouponDocumentTest extends Documentation {

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
                getDocumentResponse(),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION).description("Access Token")
                ),
                requestFields(
                    fieldWithPath("receiverIds").type(JsonFieldType.ARRAY)
                        .description("쿠폰 받을 사람들의 ID 리스트"),
                    fieldWithPath("hashtag").type(JsonFieldType.STRING)
                        .description("쿠폰 해시태그"),
                    fieldWithPath("description").type(JsonFieldType.STRING)
                        .description("쿠폰 추가 설명"),
                    fieldWithPath("couponType").type(JsonFieldType.STRING)
                        .description("쿠폰 타입")
                ),
                responseFields(
                    fieldWithPath("data.[].id").type(JsonFieldType.NUMBER)
                        .description("쿠폰 ID"),
                    fieldWithPath("data.[].senderId").type(JsonFieldType.NUMBER)
                        .description("쿠폰을 보낸 사람의 ID"),
                    fieldWithPath("data.[].senderNickname").type(JsonFieldType.STRING)
                        .description("쿠폰을 보낸 사람의 닉네임"),
                    fieldWithPath("data.[].receiverId").type(JsonFieldType.NUMBER)
                        .description("쿠폰을 받을 사람의 ID"),
                    fieldWithPath("data.[].receiverNickname").type(JsonFieldType.STRING)
                        .description("쿠폰을 받을 사람의 닉네임"),
                    fieldWithPath("data.[].hashtag").type(JsonFieldType.STRING)
                        .description("쿠폰 해시태그"),
                    fieldWithPath("data.[].description").type(JsonFieldType.STRING)
                        .description("쿠폰 추가 설명"),
                    fieldWithPath("data.[].couponType").type(JsonFieldType.STRING)
                        .description("쿠폰 타입"),
                    fieldWithPath("data.[].couponStatus").type(JsonFieldType.STRING)
                        .description("쿠폰 상태")
                )
            ));
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
                getDocumentResponse(),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION).description("Access Token")
                ),
                responseFields(
                    fieldWithPath("data.received.[].couponId").type(JsonFieldType.NUMBER)
                        .description("받은 쿠폰 ID"),
                    fieldWithPath("data.received.[].reservationId").type(JsonFieldType.NUMBER)
                        .description("예약 ID"),
                    fieldWithPath("data.received.[].memberId").type(JsonFieldType.NUMBER)
                        .description("받은 사람 ID"),
                    fieldWithPath("data.received.[].nickname").type(JsonFieldType.STRING)
                        .description("받은 사람 닉네임"),
                    fieldWithPath("data.received.[].hashtag").type(JsonFieldType.STRING)
                        .description("받은 쿠폰 해시태그"),
                    fieldWithPath("data.received.[].description").type(JsonFieldType.STRING)
                        .description("받은 쿠폰 설명"),
                    fieldWithPath("data.received.[].couponType").type(JsonFieldType.STRING)
                        .description("받은 쿠폰 타입"),
                    fieldWithPath("data.received.[].couponStatus").type(JsonFieldType.STRING)
                        .description("받은 쿠폰 상태"),
                    fieldWithPath("data.received.[].message").type(JsonFieldType.STRING)
                        .description("예약 메시지"),
                    fieldWithPath("data.received.[].meetingDate").type(JsonFieldType.STRING)
                        .description("예약 시간"),

                    fieldWithPath("data.sent.[].couponId").type(JsonFieldType.NUMBER)
                        .description("보낸 쿠폰 ID"),
                    fieldWithPath("data.sent.[].reservationId").type(JsonFieldType.NUMBER)
                        .description("예약 ID"),
                    fieldWithPath("data.sent.[].memberId").type(JsonFieldType.NUMBER)
                        .description("보낸 사람 ID"),
                    fieldWithPath("data.sent.[].nickname").type(JsonFieldType.STRING)
                        .description("보낸 사람 닉네임"),
                    fieldWithPath("data.sent.[].hashtag").type(JsonFieldType.STRING)
                        .description("보낸 쿠폰 해시태그"),
                    fieldWithPath("data.sent.[].description").type(JsonFieldType.STRING)
                        .description("보낸 쿠폰 설명"),
                    fieldWithPath("data.sent.[].couponType").type(JsonFieldType.STRING)
                        .description("보낸 쿠폰 타입"),
                    fieldWithPath("data.sent.[].couponStatus").type(JsonFieldType.STRING)
                        .description("보낸 쿠폰 상태"),
                    fieldWithPath("data.sent.[].message").type(JsonFieldType.STRING)
                        .description("예약 메시지"),
                    fieldWithPath("data.sent.[].meetingDate").type(JsonFieldType.STRING)
                        .description("예약 시간")
                ))
            );
    }
}
