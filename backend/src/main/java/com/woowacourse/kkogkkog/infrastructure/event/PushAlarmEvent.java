package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.legacy_member.domain.LegacyMemberHistory;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import lombok.Getter;

@Getter
public class PushAlarmEvent {

    private final String botAccessToken;
    private final String hostMemberId;
    private final String message;
    private final CouponEventType couponEventType;

    public PushAlarmEvent(final String botAccessToken,
                          final String hostMemberId,
                          final String message,
                          final CouponEventType couponEventType) {
        this.botAccessToken = botAccessToken;
        this.hostMemberId = hostMemberId;
        this.message = message;
        this.couponEventType = couponEventType;
    }

    public static PushAlarmEvent of(CouponHistory couponHistory) {
        Member hostMember = couponHistory.getHostMember();
        Workspace workspace = hostMember.getWorkspace();
        return new PushAlarmEvent(workspace.getAccessToken(), hostMember.getUserId(),
            couponHistory.toNoticeMessage(), couponHistory.getCouponEventType());
    }

    // TODO: should be deleted
    public static PushAlarmEvent of(LegacyMemberHistory couponHistory) {
        Member hostMember = couponHistory.getHostMember();
        Workspace workspace = hostMember.getWorkspace();
        return new PushAlarmEvent(workspace.getAccessToken(), hostMember.getUserId(),
            couponHistory.toNoticeMessage(), couponHistory.getCouponEvent());
    }

    public boolean shouldNotSendPushAlarm() {
        return botAccessToken == null || couponEventType == CouponEventType.FINISH;
    }
}
