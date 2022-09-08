package com.woowacourse.kkogkkog.member.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCreateResponse {

    private String accessToken;

    public MemberCreateResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
