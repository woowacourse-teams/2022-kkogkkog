package com.woowacourse.kkogkkog.exception.auth;

import com.woowacourse.kkogkkog.exception.UnauthenticatedException;

public class AccessTokenRetrievalFailedException extends UnauthenticatedException {

    public AccessTokenRetrievalFailedException(String message) {
        super(message);
    }
}
