package com.woowacourse.kkogkkog.exception.member;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class MemberDuplicatedEmail extends InvalidRequestException {

    public MemberDuplicatedEmail() {
        super("이미 존재하는 이메일 입니다.");
    }
}
