package com.woowacourse.kkogkkog.exception.auth;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class OAuthUserInfoRequestFailedException extends InvalidRequestException {

    public OAuthUserInfoRequestFailedException() {
        super("유효하지 않은 토큰입니다.");
    }
}
