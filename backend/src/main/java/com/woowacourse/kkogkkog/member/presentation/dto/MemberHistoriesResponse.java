package com.woowacourse.kkogkkog.member.presentation.dto;

import com.woowacourse.kkogkkog.member.application.dto.MemberHistoryResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberHistoriesResponse {

    private List<MemberHistoryResponse> data;

    public MemberHistoriesResponse(List<MemberHistoryResponse> data) {
        this.data = data;
    }
}
