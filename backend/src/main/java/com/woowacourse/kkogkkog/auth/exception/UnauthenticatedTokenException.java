package com.woowacourse.kkogkkog.auth.exception;

import com.woowacourse.kkogkkog.common.exception.UnauthenticatedException;

public class UnauthenticatedTokenException extends UnauthenticatedException {

    public UnauthenticatedTokenException() {
        super("토큰이 유효하지 않습니다.");
    }
}
