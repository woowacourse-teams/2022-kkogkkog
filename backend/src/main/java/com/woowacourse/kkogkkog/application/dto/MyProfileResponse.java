package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyProfileResponse {

    private Long id;
    private String userId;
    private String workspaceId;
    private String workspaceName;
    private String nickname;
    private String email;
    private String imageUrl;
    private Long unReadCount;

    public MyProfileResponse(Long id, String userId, String workspaceId, String workspaceName,
                             String nickname, String email, String imageUrl, Long unReadCount) {
        this.id = id;
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.workspaceName = workspaceName;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
        this.unReadCount = unReadCount;
    }

    public static MyProfileResponse of(Member member, String workspaceName,
                                       Long unreadHistoryCount) {
        return new MyProfileResponse(
            member.getId(),
            member.getUserId(),
            member.getWorkspaceId(),
            workspaceName,
            member.getNickname(),
            member.getEmail(),
            member.getImageUrl(),
            unreadHistoryCount
        );
    }
}
