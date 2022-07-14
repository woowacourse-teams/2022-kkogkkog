package com.woowacourse.kkogkkog.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenResponse {

    private String accessToken;

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
