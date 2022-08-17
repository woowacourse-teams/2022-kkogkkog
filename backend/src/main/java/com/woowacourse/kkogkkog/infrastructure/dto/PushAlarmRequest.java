package com.woowacourse.kkogkkog.infrastructure.dto;

import lombok.Getter;

@Getter
public class PushAlarmRequest {

    private static final String KKOGKKOG_PROD_URL = "https://kkogkkog.com";
    private static final String ATTACHMENTS = "[\n"
        + "{\n"
        + "\"color\": \"#FF9620\",\n"
        + "\"title\": \"꼭꼭 바로가기\",\n"
        + "\"title_link\": \"" + KKOGKKOG_PROD_URL + "\",\n"
        + "\"thumb_url\": \"" + KKOGKKOG_PROD_URL + "\",\n"
        + "\"footer\": \"꼭꼭\",\n"
        + "\"footer_icon\": \"https://user-images.githubusercontent.com/76774809/185035828-53e85e49-d388-4e18-be44-a10b91ece8a3.jpeg\",\n"
        + "}\n"
        + "]";

    private final String channel;
    private final String text;
    private final String attachments;

    private PushAlarmRequest(String channel, String text, String attachments) {
        this.channel = channel;
        this.text = text;
        this.attachments = attachments;
    }

    public static PushAlarmRequest of(String channel, String text) {
        return new PushAlarmRequest(channel, text, ATTACHMENTS);
    }
}
