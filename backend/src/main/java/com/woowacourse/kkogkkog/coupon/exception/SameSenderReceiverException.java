package com.woowacourse.kkogkkog.coupon.exception;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class SameSenderReceiverException extends InvalidRequestException {

    public SameSenderReceiverException() {
        super("보낸 사람과 받는 사람이 같을 수 없습니다.");
    }
}
