package com.woowacourse.kkogkkog.exception.infrastructure;

public class PostMessageRequestFailedException extends RuntimeException {

    public PostMessageRequestFailedException() {
        super("슬랙 DM 전송 요청에 실패하였습니다.");
    }
}
