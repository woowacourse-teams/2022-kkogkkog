package com.woowacourse.kkogkkog.domain;

import java.security.SecureRandom;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Nickname {

    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();

    @Column(name = "nickname", nullable = false)
    private String value;

    public Nickname(String value) {
        this.value = value;
    }

    public static Nickname ofRandom() {
        String randomNum = String.valueOf(RANDOM_GENERATOR.nextDouble());
        return new Nickname(String.format("익명%s", randomNum.substring(2, 6)));
    }
}
