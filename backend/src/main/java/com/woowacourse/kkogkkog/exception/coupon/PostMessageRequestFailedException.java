package com.woowacourse.kkogkkog.exception.coupon;

public class PostMessageRequestFailedException extends RuntimeException{

    public PostMessageRequestFailedException() {
        super("슬랙 DM 알림 요청을 실패하였습니다.");
    }
}
