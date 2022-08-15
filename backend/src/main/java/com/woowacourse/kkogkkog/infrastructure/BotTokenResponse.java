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
    private TeamResponse team;
    private String error = "";

    public BotTokenResponse(Boolean ok, String accessToken, String workspaceId, String workspaceName) {
        this.ok = ok;
        this.accessToken = accessToken;
        this.team = new TeamResponse(workspaceId, workspaceName);
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class TeamResponse {

        private String id;
        private String name;

        public TeamResponse(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Override
    public String toString() {
        return "BotTokenResponse{" +
            "ok=" + ok +
            ", accessToken='" + accessToken + '\'' +
            ", team=" + team +
            ", error='" + error + '\'' +
            '}';
    }
}
