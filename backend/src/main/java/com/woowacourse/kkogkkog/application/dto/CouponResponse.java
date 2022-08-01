package com.woowacourse.kkogkkog.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.domain.Coupon;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponResponse {

    private Long id;
    private CouponMemberResponse sender;
    private CouponMemberResponse receiver;
    private String modifier;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;
    private String message;
    private String backgroundColor;
    private String couponType;
    private String couponStatus;

    public CouponResponse(Long id, CouponMemberResponse sender,
                          CouponMemberResponse receiver, String modifier, String message,
                          String backgroundColor, String couponType, String couponStatus) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.modifier = modifier;
        this.message = message;
        this.backgroundColor = backgroundColor;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
    }

    public CouponResponse(Long id, CouponMemberResponse sender,
                          CouponMemberResponse receiver, String modifier,
                          LocalDate meetingDate, String message, String backgroundColor,
                          String couponType, String couponStatus) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.modifier = modifier;
        this.meetingDate = meetingDate;
        this.message = message;
        this.backgroundColor = backgroundColor;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
    }

    public static CouponResponse of(Coupon coupon) {
        return new CouponResponse(
            coupon.getId(),
            CouponMemberResponse.of(coupon.getSender()),
            CouponMemberResponse.of(coupon.getReceiver()),
            coupon.getModifier(),
            coupon.getMeetingDate(),
            coupon.getMessage(),
            coupon.getBackgroundColor(),
            coupon.getCouponType().name(),
            coupon.getCouponStatus().name());
    }
}
