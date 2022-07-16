package com.woowacourse.kkogkkog.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MembersResponse {

    private List<MemberResponse> data;

    public MembersResponse(List<MemberResponse> data) {
        this.data = data;
    }
}
