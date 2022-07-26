package com.woowacourse.kkogkkog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.exception.member.MemberDuplicatedEmail;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.fixture.MemberFixture;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DisplayName("MemberService 클래스의")
class MemberServiceTest2 extends ServiceTest {

    @Autowired
    private MemberService memberService;

    @Nested
    @DisplayName("save 메서드는")
    class Save {

        @Test
        @DisplayName("회원 정보를 받으면, 회원을 저장하고 저장된 Id를 반환한다.")
        void success() {
            MemberCreateRequest memberCreateRequest = new MemberCreateRequest("email@gmail.com",
                "password1234!",
                "nickname");

            Long memberId = memberService.save(memberCreateRequest);

            assertThat(memberId).isNotNull();
        }

        @Test
        @DisplayName("중복된 이메일이 존재하면, 예외를 던진다.")
        void fail_duplicatedEmail() {
            MemberCreateRequest memberCreateRequest = new MemberCreateRequest("email@gmail.com",
                "password1234!",
                "nickname");

            memberService.save(memberCreateRequest);
            assertThatThrownBy(() -> memberService.save(memberCreateRequest))
                .isInstanceOf(MemberDuplicatedEmail.class);
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class FindById {

        @Test
        @DisplayName("저장된 회원의 Id를 받으면, 해당 회원의 정보를 반환한다.")
        void success() {
            MemberCreateRequest memberCreateRequest = new MemberCreateRequest("email@gmail.com",
                "password1234!",
                "nickname");
            Long memberId = memberService.save(memberCreateRequest);

            MemberResponse memberResponse = memberService.findById(memberId);

            assertThat(memberResponse).usingRecursiveComparison().ignoringFields("id").isEqualTo(
                new MemberResponse(null, "email@gmail.com", "nickname")
            );
        }

        @Test
        @DisplayName("존재하지 않는 회원 Id를 받으면, 예외를 던진다.")
        void fail_noExistMember() {
            Member nonExistingMember = MemberFixture.NON_EXISTING_MEMBER;

            Assertions.assertThatThrownBy(
                () -> memberService.findById(nonExistingMember.getId())
            ).isInstanceOf(MemberNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class FindAll {

        @Test
        @DisplayName("회원가입된 모든 회원들의 정보를 반환한다.")
        void success() {
            MemberCreateRequest memberCreateRequest1 = new MemberCreateRequest("email1@gmail.com",
                "password1234!",
                "nickname1");
            MemberCreateRequest memberCreateRequest2 = new MemberCreateRequest("email2@gmail.com",
                "password1234!",
                "nickname2");
            memberService.save(memberCreateRequest1);
            memberService.save(memberCreateRequest2);

            List<MemberResponse> membersResponse = memberService.findAll();

            assertThat(membersResponse).hasSize(2);
        }
    }
}
