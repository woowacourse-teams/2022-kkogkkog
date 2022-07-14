package com.woowacourse.kkogkkog.presentation.dto;

import com.woowacourse.kkogkkog.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCreateRequest {

    private String email;
    private String password;
    private String nickname;

    public MemberCreateRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public Member toEntity() {
        return new Member(null, this.email, this.password, this.nickname);
    }
}
