package com.woowacourse.kkogkkog.infrastructure.dto;

import com.woowacourse.kkogkkog.member.domain.Workspace;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class WorkspaceResponse {

    private String workspaceId;
    private String workspaceName;
    private String accessToken;

    public WorkspaceResponse(String workspaceId, String workspaceName, String accessToken) {
        this.workspaceId = workspaceId;
        this.workspaceName = workspaceName;
        this.accessToken = accessToken;
    }

    public static WorkspaceResponse of(Workspace workspace) {
        return new WorkspaceResponse(workspace.getWorkspaceId(), workspace.getName(), workspace.getAccessToken());
    }
}
