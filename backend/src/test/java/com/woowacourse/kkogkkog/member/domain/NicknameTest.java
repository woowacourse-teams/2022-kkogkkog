package com.woowacourse.kkogkkog.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Nickname 클래스의")
class NicknameTest {

    @Nested
    @DisplayName("생성자는")
    class Constructor {

        @ParameterizedTest
        @DisplayName("1~6글자 사이의 한글, 숫자, 영문자로 구성된 문자열을 받는다.")
        @ValueSource(strings = {"a", "가aB1", "여섯글자이름"})
        void success(String nickname) {
            assertThatNoException().isThrownBy(() -> new Nickname(nickname));
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
        @DisplayName("'형용사동물명'이라는 형식의 닉네임을 생성한다.")
        void success() {
            Nickname nickname = Nickname.ofRandom();
            String actual = nickname.getValue();

            assertThat(actual).matches("^[가-힣a-zA-Z0-9]{1,6}$");
        }
    }
}
