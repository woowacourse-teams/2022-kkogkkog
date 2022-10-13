package com.woowacourse.kkogkkog.infrastructure.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WoowacourseUsersResponse {

    private List<WoowacourseUserResponse> members;

    public WoowacourseUsersResponse(List<WoowacourseUserResponse> members) {
        this.members = members;
    }
}
