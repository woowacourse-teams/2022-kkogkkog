package com.woowacourse.kkogkkog.exception.auth;

import com.woowacourse.kkogkkog.exception.NotFoundException;

public class WorkspaceNotFoundException extends NotFoundException {

    public WorkspaceNotFoundException() {
        super("존재하지 않는 워크스페이스입니다.");
    }
}
