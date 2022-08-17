package com.woowacourse.kkogkkog.exception.infrastructure;

import com.woowacourse.kkogkkog.exception.UnauthenticatedException;

public class AccessTokenRetrievalFailedException extends UnauthenticatedException {

    public AccessTokenRetrievalFailedException() {
        super("슬랙 서버로부터 토큰 조회에 실패하였습니다.");
    }
}
