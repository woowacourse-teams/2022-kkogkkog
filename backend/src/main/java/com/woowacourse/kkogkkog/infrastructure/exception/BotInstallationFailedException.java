package com.woowacourse.kkogkkog.infrastructure.exception;

public class BotInstallationFailedException extends RuntimeException {

    public BotInstallationFailedException() {
        super("슬랙 봇 등록에 실패하였습니다.");
    }

    public BotInstallationFailedException(String message) {
        super(String.format("슬랙 봇 등록에 실패하였습니다. [%s]", message));
    }
}
