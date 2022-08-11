package com.woowacourse.kkogkkog.domain;

import static com.woowacourse.kkogkkog.fixture.WorkspaceFixture.WORKSPACE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Coupon 클래스의")
public class MemberTest {

    @Nested
    @DisplayName("ofRandomNickname 정적 팩토리 메서드는")
    class OfRandomNickname {

        @Test
        @DisplayName("익명1234라는 형식의 닉네임을 지닌 회원을 생성한다.")
        void success() {
            Member member = Member.ofRandomNickname("UJeong", WORKSPACE,
                "jeong@gmail.com", "image");

            String actual = member.getNickname();
            assertThat(actual.matches("^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,6}$")).isTrue();
        }
    }
}
