package com.woowacourse.kkogkkog.application;

import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.ROOKIE;
import static com.woowacourse.kkogkkog.common.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_저장_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.kkogkkog.auth.application.dto.MemberCreateResponse;
import com.woowacourse.kkogkkog.application.dto.MemberHistoryResponse;
import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.application.dto.MemberUpdateRequest;
import com.woowacourse.kkogkkog.application.dto.MyProfileResponse;
import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.domain.WorkspaceUser;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.domain.repository.WorkspaceUserRepository;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.fixture.WorkspaceFixture;
import com.woowacourse.kkogkkog.infrastructure.dto.SlackUserInfo;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DisplayName("MemberService 클래스의")
class MemberServiceTest extends ServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WorkspaceUserRepository workspaceUserRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private CouponService couponService;

    private Workspace workspace;

    @BeforeEach
    void setup() {
        workspace = workspaceRepository.save(WorkspaceFixture.KKOGKKOG.getWorkspace());
    }

    @Nested
    @DisplayName("saveOrUpdate 메서드는")
    class SaveOrUpdate {

        @Test
        @DisplayName("가입되지 않은 이메일 정보를 받으면 신규 회원과 워크스페이스 계정을 저장한다.")
        void initialSignUp() {
            SlackUserInfo slackUserInfo = new SlackUserInfo("URookie", null, null, "루키",
                "rookie@gmail.com", "image");
            MemberCreateResponse response = memberService.saveOrUpdate(slackUserInfo, workspace);
            Optional<Member> savedMember = memberRepository.findByEmail("rookie@gmail.com");
            Optional<WorkspaceUser> savedWorkspaceUser = workspaceUserRepository.findByUserId(
                "URookie");

            assertAll(
                () -> assertThat(response.getId()).isNotNull(),
                () -> assertThat(response.getIsNew()).isTrue(),
                () -> assertThat(savedMember).isPresent(),
                () -> assertThat(savedWorkspaceUser).isPresent()
            );
        }

        @Test
        @DisplayName("가입된 워크스페이스 계정 정보를 받으면, 해당 회원의 Id와 회원가입 여부를 참으로 반환한다.")
        void signIn() {
            SlackUserInfo slackUserInfo = new SlackUserInfo("URookie", null, null, "루키",
                "rookie@gmail.com", "image");
            memberService.saveOrUpdate(slackUserInfo, workspace);

            MemberCreateResponse response = memberService.saveOrUpdate(slackUserInfo, workspace);

            assertAll(
                () -> assertThat(response.getId()).isNotNull(),
                () -> assertThat(response.getIsNew()).isFalse()
            );
        }

        @Test
        @DisplayName("미가입된 워크스페이스 계정 정보와 가입된 회원의 이메일을 받으면, 해당 회원에 연관된 워크스페이스 계정을 저장한다.")
        void memberIntegration() {
            SlackUserInfo slackUserInfo = new SlackUserInfo("URookie", null, null, "루키",
                "rookie@gmail.com", "image");
            SlackUserInfo slackUserInfo2 = new SlackUserInfo("URookie2", null, null, "루키",
                "rookie@gmail.com", "image2");

            memberService.saveOrUpdate(slackUserInfo, workspace);
            MemberCreateResponse response = memberService.saveOrUpdate(slackUserInfo2, workspace);
            Member savedMember = memberRepository.findByEmail("rookie@gmail.com").get();
            Optional<WorkspaceUser> integratedWorkspaceUser = workspaceUserRepository.findByUserId(
                "URookie2");

            assertAll(
                () -> assertThat(response.getId()).isNotNull(),
                () -> assertThat(response.getIsNew()).isFalse(),
                () -> assertThat(savedMember.getUserId()).isEqualTo("URookie2"),
                () -> assertThat(savedMember.getImageUrl()).isEqualTo("image2"),
                () -> assertThat(integratedWorkspaceUser).isPresent()
            );
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class FindById {

        @Test
        @DisplayName("저장된 회원의 Id를 받으면, 해당 회원의 정보를 반환한다.")
        void success() {
            Member member = memberRepository.save(ROOKIE.getMember());
            MyProfileResponse memberResponse = memberService.findById(member.getId());

            assertThat(memberResponse).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(MyProfileResponse.of(member, 0L));
        }

        @Test
        @DisplayName("존재하지 않는 회원 Id를 받으면, 예외를 던진다.")
        void fail_noExistMember() {
            Member nonExistingMember = SENDER.getMember(2L);

            Assertions.assertThatThrownBy(() -> memberService.findById(nonExistingMember.getId()))
                .isInstanceOf(MemberNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class FindAll {

        @Test
        @DisplayName("회원가입된 모든 회원들의 정보를 반환한다.")
        void success() {
            memberRepository.save(ROOKIE.getMember(workspace));
            memberRepository.save(AUTHOR.getMember(workspace));

            List<MemberResponse> membersResponse = memberService.findAll();

            assertThat(membersResponse).hasSize(2);
        }
    }

    @Nested
    @DisplayName("findHistoryById 메서드는")
    class FindHistoryById {

        @Test
        @DisplayName("사용자의 history를 반환한다.")
        void success() {
            Member rookie = memberRepository.save(ROOKIE.getMember(workspace));
            Member arthur = memberRepository.save(AUTHOR.getMember(workspace));
            couponService.save(COFFEE_쿠폰_저장_요청(rookie.getId(), List.of(arthur.getId())));

            List<MemberHistoryResponse> actual = memberService.findHistoryById(arthur.getId());

            assertThat(actual).hasSize(1);
        }
    }

    @Nested
    @DisplayName("updateIsReadMemberHistory 메서드는")
    class UpdateIsReadMemberHistory {

        @Test
        @DisplayName("요청받을 경우 true 로 변경된다.")
        void success() {
            Member rookie = memberRepository.save(ROOKIE.getMember(workspace));
            Member arthur = memberRepository.save(AUTHOR.getMember(workspace));

            couponService.save(COFFEE_쿠폰_저장_요청(rookie.getId(), List.of(arthur.getId())));

            assertDoesNotThrow(() -> memberService.updateIsReadMemberHistory(1L));
        }
    }

    @Nested
    @DisplayName("updateAllIsReadMemberHistories 메서드는")
    class updateAllIsReadMemberHistories {

        @Test
        @DisplayName("나의 모든 히스토리의 읽음 여부를 True 로 변경한다.")
        void success() {
            Member rookie = memberRepository.save(ROOKIE.getMember(workspace));
            Member leo = memberRepository.save(LEO.getMember(workspace));
            Member arthur = memberRepository.save(AUTHOR.getMember(workspace));

            couponService.save(COFFEE_쿠폰_저장_요청(rookie.getId(), List.of(arthur.getId())));
            couponService.save(COFFEE_쿠폰_저장_요청(leo.getId(), List.of(arthur.getId())));

            assertDoesNotThrow(() -> memberService.updateAllIsReadMemberHistories(arthur.getId()));
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Update {

        @Test
        @DisplayName("사용자의 닉네임을 수정한다.")
        void success() {
            Long memberId = memberRepository.save(ROOKIE.getMember(workspace)).getId();
            String expected = "새로운닉네임";

            memberService.update(new MemberUpdateRequest(memberId, expected));
            String actual = memberService.findById(memberId).getNickname();

            assertThat(actual).isEqualTo(expected);
        }
    }
}
