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

    private String name;
    private String email;

    @JsonProperty(SLACK_URI + "/user_image_512")
    private String userImageUrl;

    @JsonProperty(SLACK_URI + "/team_id")
    private String teamId;

    @JsonProperty(SLACK_URI + "/team_name")
    private String teamName;

    @JsonProperty(SLACK_URI + "/team_image_230")
    private String teamImageUrl;

    public SlackUserInfo(String userId, String name, String email, String userImageUrl,
                         String teamId,
                         String teamName, String teamImageUrl) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userImageUrl = userImageUrl;
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamImageUrl = teamImageUrl;
    }
}
