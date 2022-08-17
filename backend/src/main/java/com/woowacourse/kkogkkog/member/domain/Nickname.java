package com.woowacourse.kkogkkog.member.domain;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Nickname {

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]{2,6}$");
    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();

    @Column(name = "nickname", nullable = false)
    private String value;

    public Nickname(String value) {
        Matcher matcher = NICKNAME_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new InvalidRequestException("잘못된 닉네임 형식입니다. (한글, 숫자, 영문자로 구성된 2~6글자)");
        }
        this.value = value;
    }

    public static Nickname ofRandom() {
        String randomNum = String.valueOf(RANDOM_GENERATOR.nextDouble());
        return new Nickname(String.format("익명%s", randomNum.substring(2, 6)));
    }
}
