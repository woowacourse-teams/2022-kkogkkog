package com.woowacourse.kkogkkog.application.dto;

import static com.woowacourse.kkogkkog.util.JsonFormatUtils.toLocalDate;

import com.woowacourse.kkogkkog.domain.MemberHistory;
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
    private String meetingDate;
    private Boolean isRead;
    private String createdTime;

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
        this.meetingDate = toLocalDate(meetingDate);
        this.isRead = isRead;
        this.createdTime = toLocalDate(createdTime);
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
            memberHistory.getCreatedTime()
        );
    }
}
