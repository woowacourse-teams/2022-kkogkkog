package com.woowacourse.kkogkkog.infrastructure.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class WoowacourseUsersResponse {

    private List<WoowacourseUserResponse> members;

    public void setMembers(List<WoowacourseUserResponse> members) {
        this.members = members;
    }
}
