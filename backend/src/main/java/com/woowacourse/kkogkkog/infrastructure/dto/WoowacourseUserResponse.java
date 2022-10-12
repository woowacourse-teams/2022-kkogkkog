package com.woowacourse.kkogkkog.infrastructure.dto;

import lombok.Getter;

@Getter
public class WoowacourseUserResponse {

    private String id;
    private WoowacourseProfileResponse profile;

    public void setId(String id) {
        this.id = id;
    }

    public void setProfile(WoowacourseProfileResponse profile) {
        this.profile = profile;
    }
}
