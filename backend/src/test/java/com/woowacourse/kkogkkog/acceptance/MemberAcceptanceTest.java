package com.woowacourse.kkogkkog.acceptance;

import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokeGet;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokeGetWithToken;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePatchWithToken;
import static com.woowacourse.kkogkkog.acceptance.AcceptanceContext.invokePutWithToken;
import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입_및_닉네임을_수정하고;
import static com.woowacourse.kkogkkog.acceptance.AuthAcceptanceTest.회원가입을_하고;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_생성_요청;
import static com.woowacourse.kkogkkog.core.coupon.acceptance.CouponAcceptanceTest.쿠폰_생성을_요청하고;
import static com.woowacourse.kkogkkog.fixture.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.member.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.member.application.dto.MyProfileResponse;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.presentation.dto.MemberHistoriesResponse;
import com.woowacourse.kkogkkog.member.presentation.dto.MemberUpdateMeRequest;
import com.woowacourse.kkogkkog.member.presentation.dto.MembersResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원가입된_전체_사용자_조회를_할_수_있다() {
        Workspace workspace = KKOGKKOG.getWorkspace(1L);
        Member rookie = ROOKIE.getMember(1L, workspace);
        Member author = AUTHOR.getMember(2L, workspace);
        회원가입_및_닉네임을_수정하고(rookie);
        회원가입_및_닉네임을_수정하고(author);

        ExtractableResponse<Response> extract = 전체_사용자_조회를_요청한다();
        List<MemberResponse> members = extract.body().jsonPath()
            .getList("data", MemberResponse.class);
        MembersResponse membersResponse = new MembersResponse(members);

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(membersResponse.getData()).hasSize(2),
            () -> assertThat(membersResponse.getData()).usingRecursiveComparison()
                .isEqualTo(List.of(MemberResponse.of(rookie), MemberResponse.of(author))));
    }

    @Test
    void 로그인한_경우_본인의_정보를_조회할_수_있다() {
        Workspace workspace = KKOGKKOG.getWorkspace();
        Member rookie = ROOKIE.getMember(1L, workspace);
        String rookieAccessToken = 회원가입_및_닉네임을_수정하고(rookie);
        회원가입을_하고(AUTHOR.getMember(workspace));

        ExtractableResponse<Response> extract = 본인_정보_조회를_요청한다(rookieAccessToken);
        MyProfileResponse memberResponse = extract.as(MyProfileResponse.class);

        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(memberResponse).usingRecursiveComparison()
                .isEqualTo(MyProfileResponse.of(rookie, 0L))
        );
    }

    @Test
    void 회원에게_전달된_알림들을_조회할_수_있다() {
        String rookieAccessToken = 회원가입을_하고(ROOKIE.getMember());
        String arthurAccessToken = 회원가입을_하고(JEONG.getMember());
        쿠폰_생성을_요청하고(rookieAccessToken, COFFEE_쿠폰_생성_요청(List.of(2L)));

        ExtractableResponse<Response> extract = 알림함을_확인한다(arthurAccessToken);

        MemberHistoriesResponse memberHistoriesResponse = extract.as(MemberHistoriesResponse.class);
        assertAll(
            () -> assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(memberHistoriesResponse.getData()).hasSize(1)
        );
    }

    @Test
    void 조회하지_않은_알림이면_알림의_방문상태가_변경된다() {
        String rookieAccessToken = 회원가입을_하고(ROOKIE.getMember());
        String arthurAccessToken = 회원가입을_하고(JEONG.getMember());
        쿠폰_생성을_요청하고(rookieAccessToken, COFFEE_쿠폰_생성_요청(List.of(2L)));

        ExtractableResponse<Response> extract = 알림을_체크한다(1L, arthurAccessToken);
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 나의_히스토리의_방문상태가_변경된다() {
        String rookieAccessToken = 회원가입을_하고(ROOKIE.getMember());
        String arthurAccessToken = 회원가입을_하고(JEONG.getMember());
        쿠폰_생성을_요청하고(rookieAccessToken, COFFEE_쿠폰_생성_요청(List.of(2L)));

        ExtractableResponse<Response> extract = 나의_알림을_체크한다(arthurAccessToken);
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 본인의_닉네임을_수정할_수_있다() {
        String newNickname = "새로운닉네임";
        String rookieAccessToken = 회원가입을_하고(ROOKIE.getMember());
        프로필_수정을_성공하고(newNickname, rookieAccessToken);

        ExtractableResponse<Response> extract = 본인_정보_조회를_요청한다(rookieAccessToken);

        String actual = extract.as(MyProfileResponse.class).getNickname();
        assertThat(actual).isEqualTo(newNickname);
    }

    private ExtractableResponse<Response> 본인_정보_조회를_요청한다(String accessToken) {
        return invokeGetWithToken("/api/members/me", accessToken);
    }

    private ExtractableResponse<Response> 전체_사용자_조회를_요청한다() {
        return invokeGet("/api/members");
    }

    private void 프로필_수정을_성공하고(String nickname, String accessToken) {
        프로필_수정을_성공한다(accessToken, new MemberUpdateMeRequest(nickname));
    }

    public static void 프로필_수정을_성공한다(String accessToken, MemberUpdateMeRequest body) {
        invokePutWithToken("/api/members/me", accessToken, body);
    }

    private ExtractableResponse<Response> 알림함을_확인한다(String arthurAccessToken) {
        return invokeGetWithToken("/api/members/me/histories", arthurAccessToken);
    }

    private ExtractableResponse<Response> 알림을_체크한다(Long historyId, String arthurAccessToken) {
        return invokePatchWithToken("/api/members/me/histories/" + historyId, arthurAccessToken);
    }

    private ExtractableResponse<Response> 나의_알림을_체크한다(String accessToken) {
        return invokePutWithToken("/api/members/me/histories/", accessToken);
    }
}
