package com.woowacourse.kkogkkog.coupon2.application.dto;

import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
import com.woowacourse.kkogkkog.coupon2.domain.CouponState;
import com.woowacourse.kkogkkog.coupon2.domain.CouponType;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CouponSaveRequest {

    private final Long senderId;
    private final List<Long> receiverIds;
    private final String hashtag;
    private final String description;
    private final String couponType;

    public CouponSaveRequest(Long senderId,
                             List<Long> receiverIds,
                             String hashtag,
                             String description,
                             String couponType) {
        this.senderId = senderId;
        this.receiverIds = receiverIds;
        this.hashtag = hashtag;
        this.description = description;
        this.couponType = couponType;
    }

    public List<Coupon> toEntities(Member sender, List<Member> receivers) {
        return receivers.stream()
            .map(receiver -> new Coupon(
                sender,
                receiver,
                hashtag,
                description,
                CouponType.valueOf(couponType),
                CouponState.ofReady()))
            .collect(Collectors.toList());
    }
}
