package com.woowacourse.kkogkkog.infrastructure.exception;

import com.woowacourse.kkogkkog.common.exception.UnauthenticatedException;

public class AccessTokenRetrievalFailedException extends UnauthenticatedException {

    public AccessTokenRetrievalFailedException() {
        super("서버로부터 토큰 조회에 실패하였습니다.");
    }
}
