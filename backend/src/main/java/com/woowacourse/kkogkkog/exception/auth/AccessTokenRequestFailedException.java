package com.woowacourse.kkogkkog.exception.auth;

import com.woowacourse.kkogkkog.exception.UnauthenticatedException;

public class AccessTokenRequestFailedException extends UnauthenticatedException {

    public AccessTokenRequestFailedException() {
        super("유효하지 않은 인증코드입니다.");
    }
}
