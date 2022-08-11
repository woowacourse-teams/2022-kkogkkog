package com.woowacourse.kkogkkog.domain;

import static com.woowacourse.kkogkkog.fixture.WorkspaceFixture.WORKSPACE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;
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
            Member member = Member.ofRandomNickname("UJeong",
                new MasterMember(1L, "jeong"), WORKSPACE, "jeong@gmail.com", "image");

            String actual = member.getNickname();
            assertThat(actual).matches("^[가-힣a-zA-Z0-9]{2,6}$");
        }
    }

    @Nested
    @DisplayName("updateNickname 메서드는")
    class UpdateNickname {

        private final Member member = Member.ofRandomNickname("UJeong",
            new MasterMember(1L, "jeong"), WORKSPACE, "jeong@gmail.com", "image");

        @Test
        @DisplayName("2~6글자 사이의 한글, 숫자, 영문자로 구성된 닉네임으로 수정할 수 있다.")
        void success() {
            assertThatNoException().isThrownBy(() -> member.updateNickname("가aB12"));
        }

        @Test
        @DisplayName("6글자를 초과한 닉네임으로 수정하려는 경우 예외를 발생시킨다.")
        void fail_longerThanSix() {
            assertThatThrownBy(() -> member.updateNickname("일곱글자닉네임"))
                .isInstanceOf(InvalidRequestException.class);
        }

        @Test
        @DisplayName("공백이 포함된 닉네임으로 수정하려는 경우 예외를 발생시킨다.")
        void fail_hasBlank() {
            assertThatThrownBy(() -> member.updateNickname("정 진우"))
                .isInstanceOf(InvalidRequestException.class);
        }
    }
}
