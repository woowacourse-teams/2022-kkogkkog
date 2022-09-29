package com.woowacourse.kkogkkog.auth.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserInfo {

    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("picture")
    private String picture;

    public GoogleUserInfo(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
