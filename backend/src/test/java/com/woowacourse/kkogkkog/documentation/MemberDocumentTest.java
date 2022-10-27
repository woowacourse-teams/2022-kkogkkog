package com.woowacourse.kkogkkog.documentation;

import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentRequest;
import static com.woowacourse.kkogkkog.documentation.support.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.kkogkkog.member.application.dto.MemberHistoryResponse;
import com.woowacourse.kkogkkog.member.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.member.application.dto.MyProfileResponse;
import com.woowacourse.kkogkkog.member.presentation.dto.MemberHistoriesResponse;
import com.woowacourse.kkogkkog.member.presentation.dto.MemberUpdateMeRequest;
import com.woowacourse.kkogkkog.documentation.support.DocumentTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
public class MemberDocumentTest extends DocumentTest {

    @Test
    void 회원_전체를_조회할_수_있다() throws Exception {
        // given
        List<MemberResponse> membersResponse = List.of(
            new MemberResponse(1L, "User1", "user_nickname1", "email1@gmail.com", "image"),
            new MemberResponse(2L, "User2", "user_nickname2", "email2@gmail.com", "image"));
        given(memberService.findAll()).willReturn(membersResponse);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v2/members"));

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
        MyProfileResponse memberResponse = new MyProfileResponse(1L, "User1", "TWorkspace1",
            "workspace_name", "user_nickname1", "email1@gmail.com", "image", 10L);

        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(memberService.findById(any())).willReturn(memberResponse);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v2/members/me")
            .header("Authorization", "Bearer AccessToken"));

        // then
        perform.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.userId").value("User1"))
            .andExpect(jsonPath("$.workspaceId").value("TWorkspace1"))
            .andExpect(jsonPath("$.workspaceName").value("workspace_name"))
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
                    fieldWithPath("workspaceName").type(JsonFieldType.STRING)
                        .description("워크스페이스 이름"),
                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                    fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("이미지 주소"),
                    fieldWithPath("unReadCount").type(JsonFieldType.NUMBER)
                        .description("읽지 않은 알림 개수")
                ))
            );
    }

    @Test
    void 나의_기록들을_조회할_수_있다() throws Exception {
        // given
        List<MemberHistoryResponse> historiesResponse = List.of(
            new MemberHistoryResponse(1L, "루키", "image", 1L, "COFFEE", "INIT", null, false, LocalDateTime.now()));

        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");
        given(memberService.findHistoryById(any())).willReturn(historiesResponse);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v2/members/me/histories")
            .header("Authorization", "Bearer AccessToken"));

        // then
        perform.andExpect(status().isOk())
            .andExpect(
                content().string(objectMapper.writeValueAsString(
                    new MemberHistoriesResponse(historiesResponse))));

        // docs
        perform
            .andDo(print())
            .andDo(document("member-showMeHistory",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer {accessToken}")
                ),
                responseFields(
                    fieldWithPath("data.[].id").type(JsonFieldType.NUMBER).description("기록 ID"),
                    fieldWithPath("data.[].nickname").type(JsonFieldType.STRING)
                        .description("이벤트를 보낸 사용자의 이름"),
                    fieldWithPath("data.[].imageUrl").type(JsonFieldType.STRING)
                        .description("이벤트를 보낸 사용자 프로필 주소"),
                    fieldWithPath("data.[].couponId").type(JsonFieldType.NUMBER)
                        .description("이벤트에 해당하는 쿠폰 ID"),
                    fieldWithPath("data.[].couponType").type(JsonFieldType.STRING)
                        .description("이벤트에 해당하는 쿠폰 타입"),
                    fieldWithPath("data.[].couponEvent").type(JsonFieldType.STRING)
                        .description("이벤트에 쿠폰 이벤트"),
                    fieldWithPath("data.[].meetingDate").description("이벤트의 예약 날짜"),
                    fieldWithPath("data.[].isRead").type(JsonFieldType.BOOLEAN)
                        .description("이벤트 클릭(조회) 여부"),
                    fieldWithPath("data.[].createdTime").type(JsonFieldType.STRING)
                        .description("이벤트 생성 날짜")
                ))
            );
    }

    @Test
    void 나의_모든_히스토리의_방문_여부를_업데이트_할_수_있다() throws Exception {
        // given
        doNothing().when(memberService).updateIsReadMemberHistory(any());
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");

        // when
        ResultActions perform = mockMvc.perform(put("/api/v2/members/me/histories")
            .header("Authorization", "Bearer AccessToken"));

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform
            .andDo(print())
            .andDo(document("member-updateAllMeHistories",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer {accessToken}")
                )
            ));
    }

    @Test
    void 조회된_기록들의_방문_여부를_업데이트_할_수_있다() throws Exception {
        // given
        doNothing().when(memberService).updateIsReadMemberHistory(any());
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");

        // when
        ResultActions perform = mockMvc.perform(patch("/api/v2/members/me/histories/{historyId}", 1L));

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform
            .andDo(print())
            .andDo(document("member-updateMeHistory",
                getDocumentRequest(),
                getDocumentResponse())
            );
    }

    @Test
    void 나의_닉네임_수정을_요청할_수_있다() throws Exception {
        // given
        MemberUpdateMeRequest memberUpdateMeRequest = new MemberUpdateMeRequest("새로운_닉네임");
        given(jwtTokenProvider.getValidatedPayload(any())).willReturn("1");

        // when
        ResultActions perform = mockMvc.perform(put("/api/v2/members/me")
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
