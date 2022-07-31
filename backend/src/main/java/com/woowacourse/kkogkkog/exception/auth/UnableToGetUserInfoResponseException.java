package com.woowacourse.kkogkkog.exception.auth;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class UnableToGetUserInfoResponseException extends InvalidRequestException {

    public UnableToGetUserInfoResponseException() {
        super("유효하지 않은 토큰입니다.");
    }
}
