package com.woowacourse.kkogkkog.application;

import static com.woowacourse.kkogkkog.fixture.MemberFixture.ROOKIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import com.woowacourse.kkogkkog.presentation.dto.TokenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DisplayName("AuthService 클래스의")
class AuthServiceTest2 extends ServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Nested
    @DisplayName("login 매서드는")
    class Login {

        @Test
        @DisplayName("등록된 회원 이메일과 비밀번호를 입력하면, 토큰을 반환한다.")
        void success() {
            Member rookie = ROOKIE;
            memberRepository.save(rookie);

            TokenRequest tokenRequest = new TokenRequest(rookie.getEmail(), rookie.getPassword());
            TokenResponse tokenResponse = authService.login(tokenRequest);

            assertThat(tokenResponse).isNotNull();
        }

        @Test
        @DisplayName("등록되지 않은 회원 이메일과 비밀번호를 입력하면, 예외를 던진다.")
        void fail_notEnrolled() {
            Member rookie = ROOKIE;
            TokenRequest tokenRequest = new TokenRequest(rookie.getEmail(), rookie.getPassword());
            assertThatThrownBy(
                () -> authService.login(tokenRequest)
            ).isInstanceOf(MemberNotFoundException.class);
        }
    }

}
