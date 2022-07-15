package com.woowacourse.kkogkkog.documentation;

import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
public class MemberControllerTest extends Documentation {

    @Test
    void 회원_가입을_요청한다() throws Exception {
        // given
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("email@gmail.com", "password1234!", "nickname");
        given(memberService.save(any())).willReturn(1L);

        // when
        ResultActions perform = mockMvc.perform(post("/api/members")
            .content(objectMapper.writeValueAsString(memberCreateRequest))
            .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isCreated());

        // docs
        perform
            .andDo(print())
            .andDo(document("member-create",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                ))
            );
    }

    @Test
    void 나의_회원정보를_요청할_수_있다() throws Exception {
        // given
        MemberResponse memberResponse = new MemberResponse(1L, "user1@gmail.com", "user1");

        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(memberService.findById(any())).willReturn(memberResponse);

        // when
        ResultActions perform = mockMvc.perform(get("/api/members/me")
            .header("Authorization", "Bearer AccessToken"));

        // then
        perform.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.email").value("user1@gmail.com"))
            .andExpect(jsonPath("$.nickname").value("user1"));

        // docs
        perform
            .andDo(print())
            .andDo(document("member-showMe",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer {accessToken}")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("ID"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                ))
            );
    }
}
