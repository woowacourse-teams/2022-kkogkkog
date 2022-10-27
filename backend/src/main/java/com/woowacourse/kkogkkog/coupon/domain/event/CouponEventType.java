package com.woowacourse.kkogkkog.coupon.domain.event;

import com.woowacourse.kkogkkog.common.exception.ForbiddenException;
import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.function.BiConsumer;

public enum CouponEventType {

    RECEIVE(CouponEventType::canReceive, "`%s` 님이 `%s` 쿠폰을 *받았어요*\uD83D\uDC4B"),
    INIT(CouponEventType::canInit, "`%s` 님이 `%s` 쿠폰을 *보냈어요*\uD83D\uDC4B"),
    REQUEST(CouponEventType::canRequest, "`%s` 님이 `%s` 쿠폰 사용을 *요청했어요*\uD83D\uDE4F"),
    CANCEL(CouponEventType::canCancel, "`%s` 님이 `%s` 쿠폰 사용을 *취소했어요*\uD83D\uDE10"),
    DECLINE(CouponEventType::canDecline, "`%s` 님이 `%s` 쿠폰 사용을 *거절했어요*\uD83D\uDE41"),
    ACCEPT(CouponEventType::canAccept, "`%s` 님이 `%s` 쿠폰 사용을 *승인했어요*\uD83D\uDE00"),
    FINISH(CouponEventType::canFinish, "`%s` 님이 `%s` 쿠폰 사용을 *완료했어요*\uD83D\uDC4D");

    private final BiConsumer<Boolean, Boolean> canChange;
    private final String noticeFormat;

    CouponEventType(BiConsumer<Boolean, Boolean> canChange, String noticeFormat) {
        this.canChange = canChange;
        this.noticeFormat = noticeFormat;
    }

    public static CouponEventType of(String value) {
        try {
            return CouponEventType.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidRequestException("처리할 수 없는 요청입니다.");
        }
    }

    public boolean needsMeetingDate() {
        return this == REQUEST;
    }

    public void checkExecutable(boolean isSender, boolean isReceiver) {
        canChange.accept(isSender, isReceiver);
    }

    private static void canReceive(boolean isSender, boolean isReceiver) {
        throw new InvalidRequestException("이미 발급된 쿠폰입니다.");
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
