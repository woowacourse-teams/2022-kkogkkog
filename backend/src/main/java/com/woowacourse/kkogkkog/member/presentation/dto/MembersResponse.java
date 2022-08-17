package com.woowacourse.kkogkkog.member.presentation.dto;

import com.woowacourse.kkogkkog.member.application.dto.MemberResponse;
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
