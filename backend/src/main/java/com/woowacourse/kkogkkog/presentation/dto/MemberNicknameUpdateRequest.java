package com.woowacourse.kkogkkog.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberNicknameUpdateRequest {

    private String nickname;

    public MemberNicknameUpdateRequest(String nickname) {
        this.nickname = nickname;
    }
}
