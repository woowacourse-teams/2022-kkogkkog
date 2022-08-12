package com.woowacourse.kkogkkog.coupon.application.dto;

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
    private LocalDateTime meetingDate;
    private String message;
    private LocalDateTime createdTime;

    public CouponHistoryResponse(Long id, String nickname, String imageUrl, String couponType,
                                 String couponEvent, LocalDateTime meetingDate, String message,
                                 LocalDateTime createdTime) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.couponType = couponType;
        this.couponEvent = couponEvent;
        this.meetingDate = meetingDate;
        this.message = message;
        this.createdTime = createdTime;
    }

    public static CouponHistoryResponse of(MemberHistory memberHistory) {
        return new CouponHistoryResponse(
            memberHistory.getId(),
            memberHistory.getHostMember().getNickname(),
            memberHistory.getHostMember().getImageUrl(),
            memberHistory.getCouponType().name(),
            memberHistory.getCouponEvent().name(),
            memberHistory.getMeetingDate(),
            memberHistory.getMessage(),
            memberHistory.getCreatedAt()
        );
    }

    @Override
    public String toString() {
        return "CouponHistoryResponse{" +
            "id=" + id +
            ", nickname='" + nickname + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", couponType='" + couponType + '\'' +
            ", couponEvent='" + couponEvent + '\'' +
            ", meetingDate=" + meetingDate +
            ", message='" + message + '\'' +
            ", createdTime=" + createdTime +
            '}';
    }
}
