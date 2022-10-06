package com.woowacourse.kkogkkog.documentation;

import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentResponse;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_응답;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.미등록_COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.미등록_COFFEE_쿠폰_응답;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.UnregisteredCouponsResponse;
import com.woowacourse.kkogkkog.documentation.support.DocumentTest;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
public class UnregisteredCouponDocumentTest extends DocumentTest {

    private final String BEARER_TOKEN = "Bearer {Access Token}";

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
}
