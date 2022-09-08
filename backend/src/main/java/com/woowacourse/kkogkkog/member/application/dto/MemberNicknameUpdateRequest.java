package com.woowacourse.kkogkkog.member.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberNicknameUpdateRequest {

    private Long memberId;
    private String nickname;

    public MemberNicknameUpdateRequest(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
