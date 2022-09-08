package com.woowacourse.kkogkkog.support.fixture.domain;

import com.woowacourse.kkogkkog.member.domain.Workspace;

public enum WorkspaceFixture {

    KKOGKKOG("ABC1234", "Kkogkkog", "xoxb-bot-access-token"),
    WOOWACOURSE("TFELTJB7V", "Kkogkkog", "xoxb-bot-access-token")
    ;

    private final String workspaceId;
    private final String name;
    private final String accessToken;

    WorkspaceFixture(String workspaceId, String name, String accessToken) {
        this.workspaceId = workspaceId;
        this.name = name;
        this.accessToken = accessToken;
    }

    public Workspace getWorkspace() {
        return new Workspace(null, workspaceId, name, accessToken);
    }

    public Workspace getWorkspace(Long id) {
        return new Workspace(id, workspaceId, name, accessToken);
    }

    public Workspace getWorkspace(Long id, String accessToken) {
        return new Workspace(id, workspaceId, name, accessToken);
    }
}
