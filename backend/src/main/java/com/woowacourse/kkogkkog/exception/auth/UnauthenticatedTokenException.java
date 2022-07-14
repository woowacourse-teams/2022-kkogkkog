package com.woowacourse.kkogkkog.exception.auth;

import com.woowacourse.kkogkkog.exception.UnauthorizedException;

public class UnauthenticatedTokenException extends UnauthorizedException {

    public UnauthenticatedTokenException() {
        super("토큰이 유효하지 않습니다.");
    }
}
