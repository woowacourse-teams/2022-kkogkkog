package com.woowacourse.kkogkkog.auth.documentation;

import static com.woowacourse.kkogkkog.support.documenation.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.support.documenation.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.auth.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.support.documenation.DocumentTest;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
class AuthDocumentTest extends DocumentTest {

    @Test
    void 회원가입_또는_로그인을_요청한다() throws Exception {
        // given
        TokenResponse tokenResponse = new TokenResponse("accessToken", true);
        given(authService.login(any())).willReturn(tokenResponse);

        // when
        ResultActions perform = mockMvc.perform(get("/api/login/token")
            .param("code", "code_here"));

        // then
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").value("accessToken"))
            .andExpect(jsonPath("$.isNew").value("true"));

        // docs
        perform
            .andDo(print())
            .andDo(document("auth-login",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("accessToken").type(JsonFieldType.STRING).description("토큰"),
                    fieldWithPath("isNew").type(JsonFieldType.BOOLEAN).description("회원가입 여부")
                )
            ));
    }
}