package com.woowacourse.kkogkkog.exception.auth;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class ErrorResponseToGetAccessTokenException extends InvalidRequestException {

    public ErrorResponseToGetAccessTokenException(String message) {
        super(message);
    }
}
