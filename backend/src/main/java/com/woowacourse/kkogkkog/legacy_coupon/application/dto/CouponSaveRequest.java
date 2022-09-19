package com.woowacourse.kkogkkog.legacy_coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.legacy_coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.legacy_coupon.domain.LegacyCoupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponSaveRequest {

    private Long senderId;
    private List<Long> receiverIds;
    private String hashtag;
    private String description;
    private String couponType;

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

    public List<LegacyCoupon> toEntities(Member sender, List<Member> receivers) {
        return receivers.stream()
            .map(receiver -> new LegacyCoupon(
                sender,
                receiver,
                hashtag,
                description,
                CouponType.valueOf(couponType),
                CouponStatus.READY))
            .collect(Collectors.toList());
    }
}
