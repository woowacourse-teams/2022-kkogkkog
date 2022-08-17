package com.woowacourse.kkogkkog.infrastructure.exception;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;

public class OAuthUserInfoRequestFailedException extends InvalidRequestException {

    public OAuthUserInfoRequestFailedException() {
        super("유효하지 않은 토큰입니다.");
    }
}
