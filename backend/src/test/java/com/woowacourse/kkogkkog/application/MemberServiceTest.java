package com.woowacourse.kkogkkog.application;

import static org.assertj.core.api.Assertions.assertThat;

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
        // given
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("email@gmail.com", "password1234!", "nickname");

        // when
        Long memberId = memberService.save(memberCreateRequest);

        // then
        assertThat(memberId).isNotNull();
    }
}
