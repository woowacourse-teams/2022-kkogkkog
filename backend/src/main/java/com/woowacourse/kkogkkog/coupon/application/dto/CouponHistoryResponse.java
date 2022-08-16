package com.woowacourse.kkogkkog.coupon.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.domain.MemberHistory;
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
                                 CouponEvent couponEvent, LocalDateTime meetingDate, String message,
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

    public static CouponHistoryResponse of(MemberHistory memberHistory) {
        return new CouponHistoryResponse(
            memberHistory.getId(),
            memberHistory.getHostMember().getNickname(),
            memberHistory.getHostMember().getImageUrl(),
            memberHistory.getCouponType(),
            memberHistory.getCouponEvent(),
            memberHistory.getMeetingDate(),
            memberHistory.getMessage(),
            memberHistory.getCreatedTime()
        );
    }
}
