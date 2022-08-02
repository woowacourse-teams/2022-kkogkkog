package com.woowacourse.kkogkkog.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BotTokenResponse {

    private Boolean ok;
    @JsonProperty(value = "access_token")
    private String accessToken;
    private WorkspaceResponse team;

    public BotTokenResponse(Boolean ok, String accessToken, String workspaceId, String workspaceName) {
        this.ok = ok;
        this.accessToken = accessToken;
        this.team = new WorkspaceResponse(workspaceId, workspaceName);
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class WorkspaceResponse {

        private String id;
        private String name;

        public WorkspaceResponse(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
