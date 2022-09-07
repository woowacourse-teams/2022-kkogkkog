package com.woowacourse.kkogkkog.coupon2.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
import com.woowacourse.kkogkkog.coupon2.domain.CouponState;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponResponse {

    private Long couponId;
    private Long senderId;
    private String senderNickname;
    private String senderImageUrl;
    private Long receiverId;
    private String receiverNickname;
    private String receiverImageUrl;
    private String hashtag;
    private String description;
    private String couponType;
    private String couponStatus;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetingDate;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    public CouponResponse(Long couponId,
                          Long senderId,
                          String senderNickname,
                          String senderImageUrl,
                          Long receiverId,
                          String receiverNickname,
                          String receiverImageUrl,
                          String hashtag,
                          String description,
                          String couponType,
                          String couponStatus,
                          LocalDateTime meetingDate,
                          LocalDateTime createdTime) {
        this.couponId = couponId;
        this.senderId = senderId;
        this.senderImageUrl = senderImageUrl;
        this.senderNickname = senderNickname;
        this.receiverId = receiverId;
        this.receiverNickname = receiverNickname;
        this.receiverImageUrl = receiverImageUrl;
        this.hashtag = hashtag;
        this.description = description;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
        this.meetingDate = meetingDate;
        this.createdTime = createdTime;
    }

    public static CouponResponse of(Coupon coupon) {
        Member sender = coupon.getSender();
        Member receiver = coupon.getReceiver();
        CouponState couponState = coupon.getCouponState();
        return new CouponResponse(
            coupon.getId(),
            sender.getId(),
            sender.getNickname(),
            sender.getImageUrl(),
            receiver.getId(),
            receiver.getNickname(),
            receiver.getImageUrl(),
            coupon.getHashtag(),
            coupon.getDescription(),
            coupon.getCouponType().name(),
            couponState.getCouponStatus().name(),
            couponState.getMeetingDate(),
            coupon.getCreatedTime()
        );
    }
}
