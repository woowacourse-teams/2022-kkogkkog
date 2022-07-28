package com.woowacourse.kkogkkog.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenOAuthResponse {

    private String accessToken;

    private Boolean isCreated;

    public TokenOAuthResponse(String accessToken, Boolean isCreated) {
        this.accessToken = accessToken;
        this.isCreated = isCreated;
    }
}
