package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.MemberHistory;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import lombok.Getter;

@Getter
public class PushAlarmEvent {

    private static final String WOOWACOURSE_WORKSPACE_ID = "TFELTJB7V";
    private final String workspaceId;
    private final String botAccessToken;
    private final String hostMemberId;
    private final String message;
    private final CouponEvent couponEvent;

    public PushAlarmEvent(String workspaceId, String botAccessToken, String hostMemberId, String message,
                          CouponEvent couponEvent) {
        this.workspaceId = workspaceId;
        this.botAccessToken = botAccessToken;
        this.hostMemberId = hostMemberId;
        this.message = message;
        this.couponEvent = couponEvent;
    }

    public static PushAlarmEvent of(MemberHistory memberHistory) {
        Member hostMember = memberHistory.getHostMember();
        Workspace workspace = hostMember.getWorkspace();
        return new PushAlarmEvent(workspace.getWorkspaceId(), workspace.getAccessToken(), hostMember.getUserId(),
            memberHistory.toNoticeMessage(), memberHistory.getCouponEvent());
    }

    public boolean shouldNotSendPushAlarm() {
        return couponEvent == CouponEvent.FINISH;
    }

    public boolean hasBotAccessToken() {
        return botAccessToken != null;
    }

    public boolean isWoowacourseWorkspace() {
        return WOOWACOURSE_WORKSPACE_ID.equals(workspaceId);
    }
}
