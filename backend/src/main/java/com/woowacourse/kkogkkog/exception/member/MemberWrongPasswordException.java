package com.woowacourse.kkogkkog.exception.member;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class MemberWrongPasswordException extends InvalidRequestException {

    public MemberWrongPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
