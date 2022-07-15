package com.woowacourse.kkogkkog.application;

import static com.woowacourse.kkogkkog.fixture.MemberFixture.ROOKIE;

import com.woowacourse.kkogkkog.application.dto.TokenResponse;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.presentation.dto.TokenRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class AuthServiceTest extends ServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("로그인에 성공하면 토큰을 반환한다")
    void login() {
        Member rookie = ROOKIE;
        memberRepository.save(rookie);

        TokenRequest tokenRequest = new TokenRequest(rookie.getEmail(), rookie.getPassword());
        TokenResponse tokenResponse = authService.login(tokenRequest);

        Assertions.assertThat(tokenResponse).isNotNull();
    }
}
