package com.woowacourse.kkogkkog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.application.dto.MemberResponse;
import com.woowacourse.kkogkkog.exception.member.MemberDuplicatedEmail;
import com.woowacourse.kkogkkog.presentation.dto.MemberCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입을 할 수 있다.")
    void save() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("email@gmail.com", "password1234!", "nickname");

        Long memberId = memberService.save(memberCreateRequest);

        assertThat(memberId).isNotNull();
    }

    @Test
    @DisplayName("중복된 회원의 이메일이 있을 경우 예외가 발생한다.")
    void save_fail_duplicatedEmail() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("email@gmail.com", "password1234!", "nickname");

        memberService.save(memberCreateRequest);
        assertThatThrownBy(() -> memberService.save(memberCreateRequest))
            .isInstanceOf(MemberDuplicatedEmail.class);
    }

    @Test
    @DisplayName("회원 ID를 통해 회원 정보를 조회할 수 있다.")
    void findById() {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("email@gmail.com", "password1234!", "nickname");
        Long memberId = memberService.save(memberCreateRequest);

        MemberResponse memberResponse = memberService.findById(memberId);

        assertThat(memberResponse).usingRecursiveComparison().ignoringFields("id").isEqualTo(
            new MemberResponse(null, "email@gmail.com", "nickname")
        );
    }
}
