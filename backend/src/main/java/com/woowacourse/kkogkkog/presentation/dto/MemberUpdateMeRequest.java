package com.woowacourse.kkogkkog.presentation.dto;

import com.woowacourse.kkogkkog.application.dto.MemberUpdateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberUpdateMeRequest {

    private String nickname;

    public MemberUpdateMeRequest(String nickname) {
        this.nickname = nickname;
    }

    public MemberUpdateRequest toMemberUpdateRequest(Long memberId) {
        return new MemberUpdateRequest(memberId, nickname.trim());
    }
}
