package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponMeetingData {

    private Long id;
    private CouponMemberResponse sender;
    private CouponMemberResponse receiver;
    private LocalDateTime meetingDate;
    private LocalDateTime createdTime;

    public CouponMeetingData(final Long id,
                             final CouponMemberResponse sender,
                             final CouponMemberResponse receiver,
                             final LocalDateTime meetingDate,
                             final LocalDateTime createdTime) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.meetingDate = meetingDate;
        this.createdTime = createdTime;
    }

    public static CouponMeetingData of(final Coupon coupon) {
        Member sender = coupon.getSender();
        Member receiver = coupon.getReceiver();
        return new CouponMeetingData(
            coupon.getId(),
            new CouponMemberResponse(sender.getId(), sender.getNickname(), sender.getImageUrl()),
            new CouponMemberResponse(receiver.getId(), receiver.getNickname(), receiver.getImageUrl()),
            coupon.getCouponState().getMeetingDate(),
            coupon.getCreatedTime());
    }
}
