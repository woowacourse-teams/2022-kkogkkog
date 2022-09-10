//package com.woowacourse.kkogkkog.reservation.documentation;
//
//import static com.woowacourse.kkogkkog.support.fixture.dto.ReservationDtoFixture.예약_변경_요청;
//import static com.woowacourse.kkogkkog.support.fixture.dto.ReservationDtoFixture.예약_생성_요청;
//import static com.woowacourse.kkogkkog.support.documenation.ApiDocumentUtils.getDocumentRequest;
//import static com.woowacourse.kkogkkog.support.documenation.ApiDocumentUtils.getDocumentResponse;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.woowacourse.kkogkkog.support.documenation.DocumentTest;
//import java.time.LocalDate;
//import org.apache.http.HttpHeaders;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.ResultActions;
//
//@SuppressWarnings("NonAsciiCharacters")
//class ReservationDocumentTest extends DocumentTest {
//
//    private final String BEARER_TOKEN = "Bearer {Access Token}";
//
//    @Test
//    void 예약_생성_API() throws Exception {
//        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
//        given(reservationService.save(any())).willReturn(1L);
//
//        ResultActions perform = mockMvc.perform(
//            post("/api/reservations")
//                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
//                .content(objectMapper.writeValueAsString(예약_생성_요청(1L, LocalDate.now())))
//                .contentType(MediaType.APPLICATION_JSON));
//
//        perform.andExpect(status().isCreated());
//
//        perform
//            .andDo(print())
//            .andDo(document("reservation-create",
//                getDocumentRequest(),
//                getDocumentResponse()));
//    }
//
//    @Test
//    void 예약_상태_변경_API() throws Exception {
//        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
//        doNothing().when(reservationService).update(any());
//
//        ResultActions perform = mockMvc.perform(
//            put("/api/reservations/1")
//                .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
//                .content(objectMapper.writeValueAsString(예약_변경_요청("DECLINE")))
//                .contentType(MediaType.APPLICATION_JSON));
//
//        perform.andExpect(status().isNoContent());
//
//        perform
//            .andDo(print())
//            .andDo(document("reservation-update",
//                getDocumentRequest(),
//                getDocumentResponse()));
//    }
//}
