package com.woowacourse.kkogkkog.member.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCreateRequest {

    private String accessToken;
    private String nickname;

    public MemberCreateRequest(String accessToken, String nickname) {
        this.accessToken = accessToken;
        this.nickname = nickname;
    }
}
