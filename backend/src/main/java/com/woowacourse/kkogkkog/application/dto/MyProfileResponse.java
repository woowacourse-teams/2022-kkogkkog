package com.woowacourse.kkogkkog.application.dto;

import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.Workspace;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyProfileResponse {

    private Long id;
    private String userId;
    private String nickname;
    private String email;
    private String userImageUrl;
    private String workspaceId;
    private String workspaceName;
    private String workspaceImageUrl;
    private Long unReadCount;

    public MyProfileResponse(Long id, String userId, String nickname, String email,
                             String userImageUrl,
                             String workspaceId, String workspaceName, String workspaceImageUrl,
                             Long unReadCount) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.userImageUrl = userImageUrl;
        this.workspaceId = workspaceId;
        this.workspaceName = workspaceName;
        this.workspaceImageUrl = workspaceImageUrl;
        this.unReadCount = unReadCount;
    }

    public static MyProfileResponse of(Member member, Workspace workspace, Long unreadHistoryCount) {
        return new MyProfileResponse(
            member.getId(),
            member.getUserId(),
            member.getNickname(),
            member.getEmail(),
            member.getImageUrl(),
            workspace.getWorkspaceId(),
            workspace.getName(),
            workspace.getImageUrl(),
            unreadHistoryCount
        );
    }
}
