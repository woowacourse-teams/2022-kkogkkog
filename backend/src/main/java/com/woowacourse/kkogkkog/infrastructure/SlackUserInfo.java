package com.woowacourse.kkogkkog.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SlackUserInfo {

    private static final String SLACK_URI = "https://slack.com";

    @JsonProperty(SLACK_URI + "/user_id")
    private String userId;

    @JsonProperty(SLACK_URI + "/team_id")
    private String teamId;

    @JsonProperty(SLACK_URI + "/team_name")
    private String teamName;

    private String name;
    private String email;
    private String picture;

    public SlackUserInfo(String userId, String teamId, String teamName, String name, String email,
                         String picture) {
        this.userId = userId;
        this.teamId = teamId;
        this.teamName = teamName;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
