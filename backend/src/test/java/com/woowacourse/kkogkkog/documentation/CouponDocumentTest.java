package com.woowacourse.kkogkkog.documentation;

import static com.woowacourse.kkogkkog.support.documenation.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.support.documenation.ApiDocumentUtils.getDocumentResponse;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_응답;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.coupon2.presentation.dto.CouponsCreateResponse;
import com.woowacourse.kkogkkog.coupon2.presentation.dto.CouponsResponse;
import com.woowacourse.kkogkkog.support.documenation.DocumentTest;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
class CouponDocumentTest extends DocumentTest {

    private final String BEARER_TOKEN = "Bearer {Access Token}";

    @Test
    void 쿠폰_발급_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.save(any())).willReturn(List.of(
           COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L))
        ));

        ResultActions perform = mockMvc.perform(
            post("/api/coupons")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .content(objectMapper.writeValueAsString(new CouponsCreateResponse(
                    List.of(COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L)))
                )))
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
    void 보낸_쿠폰_조회_API() throws Exception {
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(couponService.findAllBySender(any())).willReturn(List.of(
            COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L))
        ));

        ResultActions perform = mockMvc.perform(
            get("/api/coupons/send")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .queryParam("type", "type"));

        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(new CouponsResponse(
                    List.of(COFFEE_쿠폰_응답(1L, ROOKIE.getMember(1L), AUTHOR.getMember(2L)))))));

        perform
            .andDo(print())
            .andDo(document("coupon-showAll-send",
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
            get("/api/coupons/received")
                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                .queryParam("type", "type"));

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
}
