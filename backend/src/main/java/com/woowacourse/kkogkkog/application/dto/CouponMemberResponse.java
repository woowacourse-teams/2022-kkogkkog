package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponMemberResponse {

    private Long id;
    private String userId;
    private String workspaceId;
    private String nickname;
    private String email;
    private String imageUrl;

    public CouponMemberResponse(Long id, String userId, String workspaceId, String nickname,
                                String email, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static CouponMemberResponse of(Member member) {
        return new CouponMemberResponse(
            member.getId(),
            member.getUserId(),
            member.getWorkspaceId(),
            member.getNickname(),
            member.getEmail(),
            member.getImageUrl()
        );
    }
}
