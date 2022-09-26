package com.woowacourse.kkogkkog.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SlackUserTokenResponse {

    private Boolean ok;

    @JsonProperty(value = "access_token")
    private String accessToken;

    private String error;

    public SlackUserTokenResponse(Boolean ok, String accessToken, String error) {
        this.ok = ok;
        this.accessToken = accessToken;
        this.error = error;
    }

    public boolean isError() {
        return ok == null || !ok;
    }
}
