package com.woowacourse.kkogkkog.member.exception;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;

public class MemberHistoryNotFoundException extends InvalidRequestException {

    public MemberHistoryNotFoundException() {
        super("찾을 수 없는 기록입니다.");
    }
}
