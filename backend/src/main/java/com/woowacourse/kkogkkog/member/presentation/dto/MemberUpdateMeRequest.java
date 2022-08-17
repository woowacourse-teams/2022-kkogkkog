package com.woowacourse.kkogkkog.member.presentation.dto;

import com.woowacourse.kkogkkog.member.application.dto.MemberUpdateRequest;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberUpdateMeRequest {

    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    public MemberUpdateMeRequest(String nickname) {
        this.nickname = nickname;
    }

    public MemberUpdateRequest toMemberUpdateRequest(Long memberId) {
        return new MemberUpdateRequest(memberId, nickname.trim());
    }
}
