package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponResponse {

    private Long id;
    private CouponMemberResponse sender;
    private CouponMemberResponse receiver;
    private String couponTag;
    private String couponMessage;
    private String couponType;
    private String couponStatus;
    private LocalDateTime meetingDate;
    private LocalDateTime createdTime;

    public CouponResponse(final Long id,
                          final CouponMemberResponse sender,
                          final CouponMemberResponse receiver,
                          final String couponTag,
                          final String couponMessage,
                          final String couponType,
                          final String couponStatus,
                          final LocalDateTime meetingDate,
                          final LocalDateTime createdTime) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
        this.meetingDate = meetingDate;
        this.createdTime = createdTime;
    }

    public static CouponResponse of(Coupon coupon) {
        Member sender = coupon.getSender();
        Member receiver = coupon.getReceiver();
        return new CouponResponse(
            coupon.getId(),
            CouponMemberResponse.of(sender),
            CouponMemberResponse.of(receiver),
            coupon.getCouponTag(),
            coupon.getCouponMessage(),
            coupon.getCouponType().name(),
            coupon.getCouponState().getCouponStatus().name(),
            coupon.getCouponState().getMeetingDate(),
            coupon.getCreatedTime());
    }
}
