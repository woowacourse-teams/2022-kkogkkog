package com.woowacourse.kkogkkog.coupon.application.dto;

import static com.woowacourse.kkogkkog.util.JsonFormatUtils.toLocalDate;

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
    private String meetingDate;
    private String message;
    private String createdTime;

    public CouponHistoryResponse(Long id, String nickname, String imageUrl, CouponType couponType,
                                 CouponEvent couponEvent, LocalDateTime meetingDate, String message,
                                 LocalDateTime createdTime) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.couponType = couponType.name();
        this.couponEvent = couponEvent.name();
        this.meetingDate = toLocalDate(meetingDate);
        this.message = message;
        this.createdTime = toLocalDate(createdTime);
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
