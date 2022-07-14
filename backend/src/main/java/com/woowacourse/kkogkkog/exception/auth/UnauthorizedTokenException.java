package com.woowacourse.kkogkkog.exception.auth;

import com.woowacourse.kkogkkog.exception.ForbiddenException;

public class UnauthorizedTokenException extends ForbiddenException {

    public UnauthorizedTokenException() {
        super("접근할 권한이 없습니다.");
    }
}
