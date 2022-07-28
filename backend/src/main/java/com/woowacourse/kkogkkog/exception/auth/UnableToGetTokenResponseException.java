package com.woowacourse.kkogkkog.exception.auth;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class UnableToGetTokenResponseException extends InvalidRequestException {

    public UnableToGetTokenResponseException() {
        super("유효하지 않은 인증 코드 입니다.");
    }
}
