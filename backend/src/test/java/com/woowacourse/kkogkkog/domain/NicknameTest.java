package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Nickname 클래스의")
class NicknameTest {

    @Nested
    @DisplayName("ofRandom 정적 팩토리 메서드는")
    class OfRandom {

        @Test
        @DisplayName("익명1234라는 형식의 닉네임을 생성한다.")
        void success() {
            Nickname nickname = Nickname.ofRandom();
            String actual = nickname.getValue();

            assertThat(actual).startsWith("익명");
            assertThat(actual).matches("^[가-힣a-zA-Z0-9]{2,6}$");
        }
    }
}
