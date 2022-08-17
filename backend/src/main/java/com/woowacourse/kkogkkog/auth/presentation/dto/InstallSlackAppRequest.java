package com.woowacourse.kkogkkog.auth.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InstallSlackAppRequest {

    private String code;

    public InstallSlackAppRequest(String code) {
        this.code = code;
    }
}
