package com.woowacourse.kkogkkog.member.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberUpdateRequest {

    private Long memberId;
    private String nickname;

    public MemberUpdateRequest(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
