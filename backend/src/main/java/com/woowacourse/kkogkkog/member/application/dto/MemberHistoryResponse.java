package com.woowacourse.kkogkkog.member.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
import com.woowacourse.kkogkkog.coupon2.domain.CouponHistory;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberHistoryResponse {

    private Long id;
    private String nickname;
    private String imageUrl;
    private Long couponId;
    private String couponType;
    private String couponEvent;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingDate;
    private Boolean isRead;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    public MemberHistoryResponse(Long id,
                                 String nickname,
                                 String imageUrl,
                                 Long couponId,
                                 String couponType,
                                 String couponEvent,
                                 LocalDateTime meetingDate,
                                 Boolean isRead,
                                 LocalDateTime createdTime) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.couponId = couponId;
        this.couponType = couponType;
        this.couponEvent = couponEvent;
        this.meetingDate = meetingDate;
        this.isRead = isRead;
        this.createdTime = createdTime;
    }

    public static MemberHistoryResponse of(CouponHistory couponHistory) {
        Coupon coupon = couponHistory.getCoupon();
        return new MemberHistoryResponse(
            couponHistory.getId(),
            couponHistory.getTargetMember().getNickname(),
            couponHistory.getTargetMember().getImageUrl(),
            coupon.getId(),
            coupon.getCouponType().name(),
            couponHistory.getCouponEventType().name(),
            couponHistory.getMeetingDate(),
            couponHistory.getIsRead(),
            couponHistory.getCreatedTime()
        );
    }
}
