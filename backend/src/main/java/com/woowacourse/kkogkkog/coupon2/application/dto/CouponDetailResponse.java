package com.woowacourse.kkogkkog.coupon2.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
import com.woowacourse.kkogkkog.coupon2.domain.CouponHistory;
import com.woowacourse.kkogkkog.coupon2.domain.CouponState;
import com.woowacourse.kkogkkog.coupon2.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon2.domain.CouponType;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponDetailResponse {

    private Long id;
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
    private List<CouponHistoryResponse> couponHistories;

    public CouponDetailResponse(Long id, Long senderId, String senderNickname,
                                String senderImageUrl, Long receiverId, String receiverNickname,
                                String receiverImageUrl, String hashtag, String description,
                                CouponType couponType, CouponStatus couponStatus,
                                LocalDateTime meetingDate, List<CouponHistoryResponse> couponHistories) {
        this.id = id;
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.senderImageUrl = senderImageUrl;
        this.receiverId = receiverId;
        this.receiverNickname = receiverNickname;
        this.receiverImageUrl = receiverImageUrl;
        this.hashtag = hashtag;
        this.description = description;
        this.couponType = couponType.name();
        this.couponStatus = couponStatus.name();
        this.meetingDate = meetingDate;
        this.couponHistories = couponHistories;
    }

    public static CouponDetailResponse of(Coupon coupon,
                                          List<CouponHistory> couponHistories) {
        List<CouponHistoryResponse> couponHistoryResponses = couponHistories.stream()
            .map(CouponHistoryResponse::of)
            .collect(Collectors.toList());

        Member sender = coupon.getSender();
        Member receiver = coupon.getReceiver();
        CouponState couponState = coupon.getCouponState();
        return new CouponDetailResponse(
            coupon.getId(),
            sender.getId(),
            sender.getNickname(),
            sender.getImageUrl(),
            receiver.getId(),
            receiver.getNickname(),
            receiver.getImageUrl(),
            coupon.getHashtag(),
            coupon.getDescription(),
            coupon.getCouponType(),
            couponState.getCouponStatus(),
            couponState.getMeetingDate(),
            couponHistoryResponses
        );
    }
}
