package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberHistory;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PushAlarmService {

    private final static String WOOWACOURSE_ID = "TFELTJB7V";

    private final ApplicationEventPublisher publisher;

    public PushAlarmService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvent(MemberHistory memberHistory) {
        Member hostMember = memberHistory.getHostMember();
        Workspace workspace = hostMember.getWorkspace();
        if (workspace.getWorkspaceId().equals(WOOWACOURSE_ID)) {
            publisher.publishEvent(WoowacoursePushAlarmEvent.of(memberHistory));
            return;
        }
        publisher.publishEvent(PushAlarmEvent.of(memberHistory));
    }
}
