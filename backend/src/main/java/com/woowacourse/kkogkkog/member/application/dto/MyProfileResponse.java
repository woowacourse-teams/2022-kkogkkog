package com.woowacourse.kkogkkog.member.application.dto;

import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
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

    public static MyProfileResponse of(Member member,
                                       Long unreadHistoryCount) {
        Workspace workspace = member.getWorkspace();
        return new MyProfileResponse(
            member.getId(),
            member.getUserId(),
            workspace.getWorkspaceId(),
            workspace.getName(),
            member.getNickname(),
            member.getEmail(),
            member.getImageUrl(),
            unreadHistoryCount
        );
    }

    public static MyProfileResponse withNoWorkspace(Member member, Long unreadHistoryCount) {
        return new MyProfileResponse(
            member.getId(),
            null,
            null,
            null,
            member.getNickname(),
            member.getEmail(),
            member.getImageUrl(),
            unreadHistoryCount
        );
    }
}
