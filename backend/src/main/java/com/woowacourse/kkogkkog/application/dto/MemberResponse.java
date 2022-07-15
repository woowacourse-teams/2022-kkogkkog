package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberResponse {

    private Long id;
    private String email;
    private String nickname;

    public MemberResponse(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public static MemberResponse of(Member findMember) {
        return new MemberResponse(
            findMember.getId(),
            findMember.getEmail(),
            findMember.getNickname());
    }
}
