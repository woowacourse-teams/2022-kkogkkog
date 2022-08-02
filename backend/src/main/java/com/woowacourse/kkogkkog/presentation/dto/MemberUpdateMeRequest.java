package com.woowacourse.kkogkkog.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberUpdateMeRequest {

    private String nickname;

    public MemberUpdateMeRequest(String nickname) {
        this.nickname = nickname;
    }
}
