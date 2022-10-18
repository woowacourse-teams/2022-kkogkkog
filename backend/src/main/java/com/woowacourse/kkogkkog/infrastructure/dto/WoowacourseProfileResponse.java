package com.woowacourse.kkogkkog.infrastructure.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WoowacourseProfileResponse {

    private String email;

    public WoowacourseProfileResponse(String email) {
        this.email = email;
    }
}
