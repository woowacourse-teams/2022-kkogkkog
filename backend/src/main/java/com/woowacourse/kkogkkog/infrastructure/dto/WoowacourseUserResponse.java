package com.woowacourse.kkogkkog.infrastructure.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WoowacourseUserResponse {

    private String id;
    private WoowacourseProfileResponse profile;

    public WoowacourseUserResponse(String id, WoowacourseProfileResponse profile) {
        this.id = id;
        this.profile = profile;
    }
}
