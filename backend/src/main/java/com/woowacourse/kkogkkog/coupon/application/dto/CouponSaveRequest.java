package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CouponSaveRequest {

    private final Long senderId;
    private final List<Long> receiverIds;
    private final String couponTag;
    private final String couponMessage;
    private final String couponType;

    public CouponSaveRequest(Long senderId,
                             List<Long> receiverIds,
                             String couponTag,
                             String couponMessage,
                             String couponType) {
        this.senderId = senderId;
        this.receiverIds = receiverIds;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
    }

    public List<Coupon> toEntities(Member sender, List<Member> receivers) {
        return receivers.stream()
            .map(receiver -> getCoupon(sender, receiver))
            .collect(Collectors.toList());
    }

    private Coupon getCoupon(Member sender, Member receiver) {
        return new Coupon(sender, receiver, couponTag, couponMessage, CouponType.valueOf(couponType));
    }
}
