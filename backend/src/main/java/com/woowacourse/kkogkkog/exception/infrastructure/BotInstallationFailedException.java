package com.woowacourse.kkogkkog.exception.infrastructure;

public class BotInstallationFailedException extends RuntimeException {

    public BotInstallationFailedException() {
        super("슬랙 봇 등록에 실패하였습니다.");
    }
}
