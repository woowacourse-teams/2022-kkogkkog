package com.woowacourse.kkogkkog.exception.auth;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class InvalidTokenException extends InvalidRequestException {

    public InvalidTokenException() {
        super("토큰이 유효하지 않습니다.");
    }
}
