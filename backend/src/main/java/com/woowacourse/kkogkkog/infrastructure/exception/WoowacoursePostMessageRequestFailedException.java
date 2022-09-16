package com.woowacourse.kkogkkog.infrastructure.exception;

public class WoowacoursePostMessageRequestFailedException extends RuntimeException {

    public WoowacoursePostMessageRequestFailedException() {
        super("Woowacourse 워크스페이스의 슬랙 DM 전송 요청에 실패하였습니다.");
    }

    public WoowacoursePostMessageRequestFailedException(String message) {
        super(String.format("Woowacourse 워크스페이스의 슬랙 DM 전송 요청에 실패하였습니다. [%s]", message));
    }
}
