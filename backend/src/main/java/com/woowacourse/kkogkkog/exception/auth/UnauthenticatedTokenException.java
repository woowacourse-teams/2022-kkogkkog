package com.woowacourse.kkogkkog.exception.auth;

import com.woowacourse.kkogkkog.exception.UnauthenticatedException;

public class UnauthenticatedTokenException extends UnauthenticatedException {

    public UnauthenticatedTokenException() {
        super("토큰이 유효하지 않습니다.");
    }
}
