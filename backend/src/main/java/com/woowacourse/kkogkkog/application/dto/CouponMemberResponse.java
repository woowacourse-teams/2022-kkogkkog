package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponMemberResponse {

    private Long id;
    private String nickname;
    private String email;

    public CouponMemberResponse(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

    public static CouponMemberResponse of(Member member) {
        return new CouponMemberResponse(member.getId(), member.getNickname(), member.getEmail());
    }
}
