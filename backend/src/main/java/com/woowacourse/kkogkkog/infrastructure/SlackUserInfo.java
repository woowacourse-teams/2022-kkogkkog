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

    private String name;

    @JsonProperty(SLACK_URI + "/user_image_512")
    private String imageUri;

    public SlackUserInfo(String userId, String teamId, String name, String imageUri) {
        this.userId = userId;
        this.teamId = teamId;
        this.name = name;
        this.imageUri = imageUri;
    }
}
