package com.woowacourse.kkogkkog.infrastructure.exception;

public class PostMessageRequestFailedException extends RuntimeException {

    public PostMessageRequestFailedException() {
        super("슬랙 DM 전송 요청에 실패하였습니다.");
    }

    public PostMessageRequestFailedException(String message) {
        super(String.format("슬랙 DM 전송 요청에 실패하였습니다. [%s]", message));
    }
}
