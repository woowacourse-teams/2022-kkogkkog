package com.woowacourse.kkogkkog.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenRequest {

    private String email;
    private String password;

    public TokenRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
