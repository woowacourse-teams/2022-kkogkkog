package com.woowacourse.kkogkkog.infrastructure.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostSlackMessageResponse {

    private Boolean ok;
    private String error;

    public PostSlackMessageResponse(Boolean ok, String error) {
        this.ok = ok;
        this.error = error;
    }
}
