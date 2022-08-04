package com.woowacourse.kkogkkog.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import java.time.LocalDate;
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
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;
    private Boolean isRead;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    public MemberHistoryResponse(Long id,
                                 String nickname,
                                 String imageUrl,
                                 Long couponId,
                                 String couponType,
                                 String couponEvent,
                                 LocalDate meetingDate,
                                 Boolean isRead,
                                 LocalDate createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.couponId = couponId;
        this.couponType = couponType;
        this.couponEvent = couponEvent;
        this.meetingDate = meetingDate;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public static MemberHistoryResponse of(MemberHistory memberHistory) {
        return new MemberHistoryResponse(
            memberHistory.getId(),
            memberHistory.getTargetMember().getNickname(),
            memberHistory.getTargetMember().getImageUrl(),
            memberHistory.getCouponId(),
            memberHistory.getCouponType().name(),
            memberHistory.getCouponEvent().name(),
            memberHistory.getMeetingDate(),
            memberHistory.getIsRead(),
            memberHistory.getCreatedAt());
    }
}
