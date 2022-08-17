package com.woowacourse.kkogkkog.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import com.woowacourse.kkogkkog.member.domain.Nickname;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Nickname 클래스의")
class NicknameTest {

    @Nested
    @DisplayName("생성자는")
    class Constructor {

        @Test
        @DisplayName("2~6글자 사이의 한글, 숫자, 영문자로 구성된 문자열을 받는다.")
        void success() {
            assertThatNoException().isThrownBy(() -> new Nickname("가aB12"));
        }

        @Test
        @DisplayName("6글자를 초과한 닉네임으로 수정하려는 경우 예외를 발생시킨다.")
        void fail_longerThanSix() {
            assertThatThrownBy(() -> new Nickname("일곱글자닉네임"))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("공백이 포함된 닉네임으로 수정하려는 경우 예외를 발생시킨다.")
        void fail_hasBlank() {
            assertThatThrownBy(() -> new Nickname("정 진우"))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

    @Nested
    @DisplayName("ofRandom 정적 팩토리 메서드는")
    class OfRandom {

        @Test
        @DisplayName("익명1234라는 형식의 닉네임을 생성한다.")
        void success() {
            Nickname nickname = Nickname.ofRandom();
            String actual = nickname.getValue();

            assertAll(
                () -> assertThat(actual).startsWith("익명"),
                () -> assertThat(actual).matches("^[가-힣a-zA-Z0-9]{2,6}$")
            );
        }
    }
}
