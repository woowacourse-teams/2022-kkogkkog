package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberHistory;
import com.woowacourse.kkogkkog.member.domain.Workspace;

public abstract class SimplePushAlarmEvent {

    private final static String WOOWACOURSE_ID = "TFELTJB7V";

    public static SimplePushAlarmEvent handle(MemberHistory memberHistory) {
        Member hostMember = memberHistory.getHostMember();
        Workspace workspace = hostMember.getWorkspace();
        if (workspace.getWorkspaceId().equals(WOOWACOURSE_ID)) {
            return WoowacoursePushAlarmEvent.of(memberHistory);
        }
        return PushAlarmEvent.of(memberHistory);
    }
}
