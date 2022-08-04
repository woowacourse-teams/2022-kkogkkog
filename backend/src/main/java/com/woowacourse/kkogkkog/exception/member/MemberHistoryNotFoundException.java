package com.woowacourse.kkogkkog.exception.member;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;

public class MemberHistoryNotFoundException extends InvalidRequestException {

    public MemberHistoryNotFoundException() {
        super("찾을 수 없는 기록입니다.");
    }
}
