package com.woowacourse.kkogkkog.auth.exception;

import com.woowacourse.kkogkkog.common.exception.UnauthenticatedException;

public class AccessTokenRequestFailedException extends UnauthenticatedException {

    public AccessTokenRequestFailedException() {
        super("유효하지 않은 인증코드입니다.");
    }
}
