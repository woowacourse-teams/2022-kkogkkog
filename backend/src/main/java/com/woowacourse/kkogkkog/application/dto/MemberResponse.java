package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberResponse {

    private Long id;
    private String userId;
    private String workspaceId;
    private String nickname;
    private String imageUrl;

    public MemberResponse(Long id, String userId, String workspaceId, String nickname,
        String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public static MemberResponse of(Member member) {
        return new MemberResponse(
            member.getId(),
            member.getUserId(),
            member.getWorkspaceId(),
            member.getNickname(),
            member.getImageUrl()
        );
    }
}
