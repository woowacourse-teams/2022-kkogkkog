package com.woowacourse.kkogkkog.exception.member;

import com.woowacourse.kkogkkog.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException() {
        super("존재하지 않는 회원입니다.");
    }
}
