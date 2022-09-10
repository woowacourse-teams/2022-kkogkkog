package com.woowacourse.kkogkkog.coupon2.exception;

public class SameSenderReceiverException extends RuntimeException {

    public SameSenderReceiverException() {
        super("보낸 사람과 받는 사람이 같을 수 없습니다.");
    }
}
