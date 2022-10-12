package com.woowacourse.kkogkkog.documentation;

import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentResponse;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_응답;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.쿠폰_상세_내역_응답;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.쿠폰_상세_응답;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.쿠폰_이벤트_요청;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponMeetingData;
import com.woowacourse.kkogkkog.coupon.application.dto.AcceptedCouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.presentation.dto.AcceptedCouponsResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsResponse;
import com.woowacourse.kkogkkog.documentation.support.DocumentTest;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
class CouponDocumentTest extends DocumentTest {

    private final String BEARER_TOKEN = "Bearer {Access Token}";

    @Test
    void 쿠폰_생성_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.save(any())).willReturn(List.of(
            COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L))
        ));

        ResultActions perform = mockMvc.perform(
            post("/api/v2/coupons")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .content(objectMapper.writeValueAsString(COFFEE_쿠폰_생성_요청(List.of(1L, 2L))))
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isCreated())
            .andExpect(
                content().string(objectMapper.writeValueAsString(new CouponsResponse(
                    List.of(COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L)))))));

        perform
            .andDo(print())
            .andDo(document("coupon-create",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 코드_쿠폰_생성_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.saveByCouponCode(any(), any())).willReturn(
            COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L)));

        String couponCode = "쿠폰코드";
        ResultActions perform = mockMvc.perform(
            post("/api/v2/coupons/code")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .content(objectMapper.writeValueAsString(couponCode))
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
    void 보낸_쿠폰_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.findAllBySender(any())).willReturn(List.of(
            COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L))
        ));

        ResultActions perform = mockMvc.perform(
            get("/api/v2/coupons/sent")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN));

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(new CouponsResponse(
                    List.of(COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L)))))));

        perform
            .andDo(print())
            .andDo(document("coupon-showAll-sent",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 상태별_보낸_쿠폰_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.findAllBySender(any(), any())).willReturn(List.of(
            COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L))
        ));

        ResultActions perform = mockMvc.perform(
            get("/api/v2/coupons/sent/status")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .param("type", CouponStatus.READY.name())
        );

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(new CouponsResponse(
                    List.of(COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L)))))));

        perform
            .andDo(print())
            .andDo(document("coupon-showAll-send-status",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 받은_쿠폰_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.findAllByReceiver(any())).willReturn(List.of(
            COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L))
        ));

        ResultActions perform = mockMvc.perform(
            get("/api/v2/coupons/received")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN));

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(new CouponsResponse(
                    List.of(COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L)))))));

        perform
            .andDo(print())
            .andDo(document("coupon-showAll-received",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 상태별_받은_쿠폰_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.findAllByReceiver(any(), any())).willReturn(List.of(
            COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L))
        ));

        ResultActions perform = mockMvc.perform(
            get("/api/v2/coupons/received/status")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .param("type", CouponStatus.READY.name()));

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(new CouponsResponse(
                    List.of(COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L)))))));

        perform
            .andDo(print())
            .andDo(document("coupon-showAll-received-status",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 상세_쿠폰_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.find(any(), any())).willReturn(
            쿠폰_상세_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L),
                쿠폰_상세_내역_응답(1L, ROOKIE.getMember(1L)))
        );

        ResultActions perform = mockMvc.perform(
            get("/api/v2/coupons/1")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN));

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(
                    쿠폰_상세_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L),
                        쿠폰_상세_내역_응답(1L, ROOKIE.getMember(1L)))
                )));

        perform
            .andDo(print())
            .andDo(document("coupon-show",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 쿠폰_이벤트_요청_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        doNothing().when(couponService).updateStatus(any());

        ResultActions perform = mockMvc.perform(
            put("/api/v2/coupons/1/event")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .content(objectMapper.writeValueAsString(
                    쿠폰_이벤트_요청("REQUEST", LocalDateTime.now(), null)
                ))
                .contentType(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isNoContent());

        perform
            .andDo(print())
            .andDo(document("coupon-request-event",
                getDocumentRequest(),
                getDocumentResponse()));
    }

    @Test
    void 미팅이_확정된_쿠폰_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.findAcceptedCoupons(any())).willReturn(List.of(AcceptedCouponResponse.of(
            LocalDateTime.of(2022, 12, 12, 0, 0, 0),
            List.of(
                new CouponMeetingData(
                    1L,
                    new CouponMemberResponse(1L, "ROOKIE", "https://"),
                    new CouponMemberResponse(2L, "AUTHOR", "https://"),
                    null,
                    null))
        )));

        ResultActions perform = mockMvc.perform(
            get("/api/v2/coupons/accept")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN));

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(
                    new AcceptedCouponsResponse(List.of(AcceptedCouponResponse.of(
                        LocalDateTime.of(2022, 12, 12, 0, 0, 0),
                        List.of(
                            new CouponMeetingData(
                                1L,
                                new CouponMemberResponse(1L, "ROOKIE", "https://"),
                                new CouponMemberResponse(2L, "AUTHOR", "https://"),
                                null,
                                null))
                    )))
                )));

        perform
            .andDo(print())
            .andDo(document("coupon-show-accept",
                getDocumentRequest(),
                getDocumentResponse()));
    }
}
