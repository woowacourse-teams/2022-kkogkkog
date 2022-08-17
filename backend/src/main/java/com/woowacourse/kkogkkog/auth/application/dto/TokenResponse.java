package com.woowacourse.kkogkkog.auth.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenResponse {

    private String accessToken;

    private Boolean isNew;

    public TokenResponse(String accessToken, Boolean isNew) {
        this.accessToken = accessToken;
        this.isNew = isNew;
    }
}
