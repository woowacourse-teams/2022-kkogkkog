package com.woowacourse.kkogkkog.infrastructure.dto;

import lombok.Getter;

@Getter
public class WoowacourseProfileResponse {

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }
}
