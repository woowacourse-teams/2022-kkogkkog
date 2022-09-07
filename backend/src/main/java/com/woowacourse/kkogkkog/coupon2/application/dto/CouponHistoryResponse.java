package com.woowacourse.kkogkkog.coupon2.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.coupon2.domain.CouponType;
import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
import com.woowacourse.kkogkkog.coupon2.domain.CouponEventType;
import com.woowacourse.kkogkkog.coupon2.domain.CouponHistory;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponHistoryResponse {

    private Long id;
    private String nickname;
    private String imageUrl;
    private String couponType;
    private String couponEvent;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingDate;
    private String message;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    public CouponHistoryResponse(Long id, String nickname, String imageUrl, CouponType couponType,
                                 CouponEventType couponEvent, LocalDateTime meetingDate, String message,
                                 LocalDateTime createdTime) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.couponType = couponType.name();
        this.couponEvent = couponEvent.name();
        this.meetingDate = meetingDate;
        this.message = message;
        this.createdTime = createdTime;
    }

    public static CouponHistoryResponse of(CouponHistory couponHistory) {
        Coupon coupon = couponHistory.getCoupon();
        return new CouponHistoryResponse(
            couponHistory.getId(),
            couponHistory.getTargetMember().getNickname(),
            couponHistory.getTargetMember().getImageUrl(),
            coupon.getCouponType(),
            couponHistory.getCouponEventType(),
            couponHistory.getMeetingDate(),
            couponHistory.getMessage(),
            couponHistory.getCreatedTime()
        );
    }
}