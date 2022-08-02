package com.woowacourse.kkogkkog.documentation;

import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.presentation.dto.MemberUpdateMeRequest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
public class MemberControllerTest extends Documentation {

    @Test
    void 회원_전체를_조회할_수_있다() throws Exception {
        // given
        List<MemberResponse> membersResponse = List.of(
            new MemberResponse(1L, "User1", "TWorkspace1", "user_nickname1", "email1@gmail.com",
                "image"),
            new MemberResponse(2L, "User2", "TWorkspace2", "user_nickname2", "email2@gmail.com",
                "image")
        );
        given(memberService.findAll()).willReturn(membersResponse);

        // when
        ResultActions perform = mockMvc.perform(get("/api/members"));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform
            .andDo(print())
            .andDo(document("member-showAll",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("ID"),
                    fieldWithPath("data.[].userId").type(JsonFieldType.STRING).description("유저 Id"),
                    fieldWithPath("data.[].workspaceId").type(JsonFieldType.STRING)
                        .description("워크스페이스 ID"),
                    fieldWithPath("data.[].nickname").type(JsonFieldType.STRING).description("닉네임"),
                    fieldWithPath("data.[].email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("data.[].imageUrl").type(JsonFieldType.STRING)
                        .description("이미지 주소")
                ))
            );
    }

    @Test
    void 나의_회원정보를_요청할_수_있다() throws Exception {
        // given
        MemberResponse memberResponse = new MemberResponse(1L, "User1", "TWorkspace1",
            "user_nickname1", "email1@gmail.com", "image");

        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(memberService.findById(any())).willReturn(memberResponse);

        // when
        ResultActions perform = mockMvc.perform(get("/api/members/me")
            .header("Authorization", "Bearer AccessToken"));

        // then
        perform.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.userId").value("User1"))
            .andExpect(jsonPath("$.workspaceId").value("TWorkspace1"))
            .andExpect(jsonPath("$.nickname").value("user_nickname1"))
            .andExpect(jsonPath("$.email").value("email1@gmail.com"))
            .andExpect(jsonPath("$.imageUrl").value("image"));

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
                    fieldWithPath("userId").type(JsonFieldType.STRING).description("유저 Id"),
                    fieldWithPath("workspaceId").type(JsonFieldType.STRING)
                        .description("워크스페이스 ID"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("이미지 주소")
                ))
            );
    }

    @Test
    void 나의_닉네임_수정을_요청할_수_있다() throws Exception {
        // given
        MemberUpdateMeRequest memberUpdateMeRequest = new MemberUpdateMeRequest(
            "새로운_닉네임");
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");

        // when
        ResultActions perform = mockMvc.perform(put("/api/members/me")
            .header("Authorization", "Bearer accessToken")
            .content(objectMapper.writeValueAsString(memberUpdateMeRequest))
            .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform
            .andDo(print())
            .andDo(document("member-update",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer {accessToken}")
                ),
                requestFields(
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("ID")
                ))
            );
    }
}
