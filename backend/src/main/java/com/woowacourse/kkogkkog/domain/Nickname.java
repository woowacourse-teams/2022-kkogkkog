package com.woowacourse.kkogkkog.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Nickname {

    @Column(name = "nickname", nullable = false)
    private String value;

    public Nickname(String value) {
        this.value = value;
    }
}
