package com.woowacourse.kkogkkog.coupon.domain.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
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
        this.hashtag = hashtag;
        this.description = description;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
        this.message = message;
        this.meetingDate = meetingDate;
    }
}