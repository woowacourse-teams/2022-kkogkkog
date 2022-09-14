package com.woowacourse.kkogkkog.documentation;

import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.auth.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.auth.presentation.dto.InstallSlackAppRequest;
import com.woowacourse.kkogkkog.documentation.support.DocumentTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
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

    @Test
    void 슬랙_앱을_설치한다() throws Exception {
        //given
        InstallSlackAppRequest installSlackAppRequest = new InstallSlackAppRequest("code");

        // when
        ResultActions perform = mockMvc.perform(post("/api/install/bot")
            .content(objectMapper.writeValueAsString(installSlackAppRequest))
            .contentType(MediaType.APPLICATION_JSON));

        // then
        perform
            .andExpect(status().isOk());

        // docs
        perform
            .andDo(print())
            .andDo(document("slack-bot-install",
                getDocumentRequest(),
                requestFields(
                    fieldWithPath("code").type(JsonFieldType.STRING).description("인가 코드")
                )
            ));
    }
}
