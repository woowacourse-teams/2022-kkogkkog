package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.event.CouponEventType;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
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
    private LocalDateTime meetingDate;
    private String meetingMessage;
    private LocalDateTime createdTime;

    public CouponHistoryResponse(Long id,
                                 String nickname,
                                 String imageUrl,
                                 CouponType couponType,
                                 CouponEventType couponEvent,
                                 LocalDateTime meetingDate,
                                 String meetingMessage,
                                 LocalDateTime createdTime) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.couponType = couponType.name();
        this.couponEvent = couponEvent.name();
        this.meetingDate = meetingDate;
        this.meetingMessage = meetingMessage;
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
