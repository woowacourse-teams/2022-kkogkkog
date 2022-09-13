package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberHistory;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public abstract class SimplePushAlarmEvent {

    @Value("security.slack.workspace.woowacourse.team-id")
    private static String WOOWACOURSE_ID;

}
