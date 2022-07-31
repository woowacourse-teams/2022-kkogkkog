package com.woowacourse.kkogkkog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.infrastructure.MemberCreateResponse;
import com.woowacourse.kkogkkog.infrastructure.SlackUserInfo;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.fixture.MemberFixture;
import java.util.List;
import org.assertj.core.api.Assertions;
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

    @Nested
    @DisplayName("save 메서드는")
    class Save {

        @Test
        @DisplayName("가입되지 않은 회원 정보를 받으면, 회원을 저장하고 저장된 Id와 회원가입 여부를 반환한다.")
        void success_save() {
            SlackUserInfo slackUserInfo = new SlackUserInfo("URookie", "T03LX3C5540", "루키",
                "image");

            MemberCreateResponse memberCreateResponse = memberService.saveOrFind(slackUserInfo);

            assertAll(
                () -> assertThat(memberCreateResponse.getId()).isNotNull(),
                () -> assertThat(memberCreateResponse.getIsCreated()).isTrue()
            );
        }

        @Test
        @DisplayName("가입된 회원 정보를 받으면, 해당 회원의 Id와 회원가입 여부를 반환한다.")
        void success_find() {
            SlackUserInfo slackUserInfo = new SlackUserInfo("URookie", "T03LX3C5540", "루키",
                "image");

            memberService.saveOrFind(slackUserInfo);
            MemberCreateResponse memberCreateResponse = memberService.saveOrFind(slackUserInfo);

            assertAll(
                () -> assertThat(memberCreateResponse.getId()).isNotNull(),
                () -> assertThat(memberCreateResponse.getIsCreated()).isFalse()
            );
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class FindById {

        @Test
        @DisplayName("저장된 회원의 Id를 받으면, 해당 회원의 정보를 반환한다.")
        void success() {
            SlackUserInfo slackUserInfo = new SlackUserInfo("URookie", "T03LX3C5540", "루키",
                "image");
            Long memberId = memberService.saveOrFind(slackUserInfo).getId();

            MemberResponse memberResponse = memberService.findById(memberId);

            assertThat(memberResponse).usingRecursiveComparison().ignoringFields("id").isEqualTo(
                new MemberResponse(null, "URookie", "T03LX3C5540", "루키", "image")
            );
        }

        @Test
        @DisplayName("존재하지 않는 회원 Id를 받으면, 예외를 던진다.")
        void fail_noExistMember() {
            Member nonExistingMember = MemberFixture.NON_EXISTING_MEMBER;

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
            SlackUserInfo rookieUserInfo = new SlackUserInfo("URookie", "T03LX3C5540", "루키",
                "image");
            SlackUserInfo arthurUserInfo = new SlackUserInfo("UArthur", "T03LX3C5540", "아서",
                "image");

            memberService.saveOrFind(rookieUserInfo);
            memberService.saveOrFind(arthurUserInfo);

            List<MemberResponse> membersResponse = memberService.findAll();

            assertThat(membersResponse).hasSize(2);
        }
    }
}
