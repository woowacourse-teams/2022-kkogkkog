package com.woowacourse.kkogkkog.coupon.domain;

import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.exception.ForbiddenException;
import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import java.util.function.BiConsumer;

public enum CouponEvent {

    INIT(CouponEvent::canInit, "%s님이 %s 쿠폰을 보냈어요."),
    REQUEST(CouponEvent::canRequest, "%s님이 %s 쿠폰 사용을 요청했어요."),
    CANCEL(CouponEvent::canCancel, "%s님이 %s 쿠폰 사용을 취소했어요."),
    DECLINE(CouponEvent::canDecline, "%s님이 %s 쿠폰 사용을 거절했어요."),
    ACCEPT(CouponEvent::canAccept, "%s님이 %s 쿠폰 사용을 승인했어요."),
    FINISH(CouponEvent::canFinish, "%s님이 %s 쿠폰 사용을 완료했어요.");

    private final BiConsumer<Boolean, Boolean> canChange;
    private final String noticeFormat;

    CouponEvent(BiConsumer<Boolean, Boolean> canChange, String noticeFormat) {
        this.canChange = canChange;
        this.noticeFormat = noticeFormat;
    }

    public static CouponEvent of(String value) {
        try {
            return CouponEvent.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidRequestException("처리할 수 없는 요청입니다.");
        }
    }

    public void checkExecutable(boolean isSender, boolean isReceiver) {
        canChange.accept(isSender, isReceiver);
    }

    private static void canInit(boolean isSender, boolean isReceiver) {
        throw new InvalidRequestException("이미 발급된 쿠폰입니다.");
    }

    private static void canRequest(boolean isSender, boolean isReceiver) {
        if (!isReceiver) {
            throw new ForbiddenException("쿠폰을 받은 사람만 사용할 수 있습니다.");
        }
    }

    private static void canCancel(boolean isSender, boolean isReceiver) {
        if (!isSender && !isReceiver) {
            throw new ForbiddenException("쿠폰을 보낸 사람과 받은 사람만 쿠폰 사용을 취소할 수 있습니다.");
        }
    }

    private static void canDecline(boolean isSender, boolean isReceiver) {
        if (!isSender) {
            throw new ForbiddenException("쿠폰을 보낸 사람만 사용 요청을 거절할 수 있습니다.");
        }
    }

    private static void canAccept(boolean isSender, boolean isReceiver) {
        if (!isSender) {
            throw new ForbiddenException("쿠폰을 보낸 사람만 사용 요청을 수락할 수 있습니다.");
        }
    }

    private static void canFinish(boolean isSender, boolean isReceiver) {
        if (!isSender && !isReceiver) {
            throw new ForbiddenException("쿠폰을 보낸 사람과 받은 사람만 사용 완료할 수 있습니다.");
        }
    }

    public String generateNoticeMessage(Member member, CouponType couponType) {
        String noticeReceiver = member.getNickname();
        return String.format(noticeFormat, noticeReceiver, couponType.getDisplayName());
    }
}
