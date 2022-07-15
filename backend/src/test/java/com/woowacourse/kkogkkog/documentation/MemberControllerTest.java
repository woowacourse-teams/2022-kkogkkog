package com.woowacourse.kkogkkog.documentation;

import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.MembersResponse;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import java.util.List;
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
    void 회원_전체를_조회할_수_있다() throws Exception {
        // given
        MembersResponse membersResponse = new MembersResponse(List.of(
            new MemberResponse(1L,"user1@gmail.com", "user1"),
            new MemberResponse(2L, "user2@gmail.com", "user2")
        ));
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
                    fieldWithPath("data.[].email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("data.[].nickname").type(JsonFieldType.STRING).description("닉네임")
                ))
            );
    }
}
