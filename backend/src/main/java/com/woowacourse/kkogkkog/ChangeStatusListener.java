package com.woowacourse.kkogkkog;

import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.infrastructure.SlackClient;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ChangeStatusListener {

    private final MemberHistoryRepository memberHistoryRepository;
    private final SlackClient slackClient;

    public ChangeStatusListener(MemberHistoryRepository memberHistoryRepository,
                                SlackClient slackClient) {
        this.memberHistoryRepository = memberHistoryRepository;
        this.slackClient = slackClient;
    }

    @EventListener
    public void saveHistory(MemberHistory memberHistory) {
        memberHistoryRepository.save(memberHistory);
    }

    @EventListener
    public void sendNotification(MemberHistory memberHistory) {
        Member hostMember = memberHistory.getHostMember();
        Workspace workspace = hostMember.getWorkspace();
        String accessToken = workspace.getAccessToken();
        if (accessToken == null || memberHistory.shouldNotSendPushAlarm()) {
            return;
        }
        String hostMemberId = hostMember.getUserId();
        String message = memberHistory.toNoticeMessage();
        slackClient.requestPushAlarm(accessToken, hostMemberId, message);
    }
}
