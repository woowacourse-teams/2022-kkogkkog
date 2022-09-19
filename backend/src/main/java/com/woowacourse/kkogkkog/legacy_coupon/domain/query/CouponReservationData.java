package com.woowacourse.kkogkkog.legacy_coupon.domain.query;

import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.legacy_coupon.domain.CouponStatus;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponReservationData {

    private Long couponId;
    private Long reservationId;
    private Long memberId;
    private String nickname;
    private String imageUrl;
    private String hashtag;
    private String description;
    private CouponType couponType;
    private CouponStatus couponStatus;
    private String message;
    private LocalDateTime meetingDate;

    public CouponReservationData(Long couponId,
                                 Long reservationId,
                                 Long memberId,
                                 String nickname,
                                 String imageUrl,
                                 String hashtag,
                                 String description,
                                 CouponType couponType,
                                 CouponStatus couponStatus,
                                 String message,
                                 LocalDateTime meetingDate) {
        this.couponId = couponId;
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.hashtag = hashtag;
        this.description = description;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
        this.message = message;
        this.meetingDate = meetingDate;
    }
}
