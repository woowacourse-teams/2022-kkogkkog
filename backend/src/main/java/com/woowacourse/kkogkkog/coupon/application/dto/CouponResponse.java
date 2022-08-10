package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponResponse {

    private Long id;
    private Long senderId;
    private String senderNickname;
    private Long receiverId;
    private String receiverNickname;
    private String hashtag;
    private String description;
    private String couponType;
    private String couponStatus;

    public CouponResponse(Long id,
                          Long senderId,
                          String senderNickname,
                          Long receiverId,
                          String receiverNickname,
                          String hashtag,
                          String description,
                          String couponType,
                          String couponStatus) {
        this.id = id;
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.receiverId = receiverId;
        this.receiverNickname = receiverNickname;
        this.hashtag = hashtag;
        this.description = description;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
    }


    public static CouponResponse of(Coupon coupon) {
        return new CouponResponse(
            coupon.getId(),
            coupon.getSender().getId(),
            coupon.getSender().getNickname(),
            coupon.getReceiver().getId(),
            coupon.getReceiver().getNickname(),
            coupon.getHashtag(),
            coupon.getDescription(),
            coupon.getCouponType().name(),
            coupon.getCouponStatus().name()
        );
    }
}
