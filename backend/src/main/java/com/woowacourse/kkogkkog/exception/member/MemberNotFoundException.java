package com.woowacourse.kkogkkog.exception.member;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class MemberNotFoundException extends InvalidRequestException {

    public MemberNotFoundException() {
        super("존재하지 않는 회원입니다.");
    }
}
