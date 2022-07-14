package com.woowacourse.kkogkkog.exception.member;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class MemberWrongInputException extends InvalidRequestException {

    public MemberWrongInputException() {
        super("로그인 정보가 일치하지 않습니다.");
    }
}
