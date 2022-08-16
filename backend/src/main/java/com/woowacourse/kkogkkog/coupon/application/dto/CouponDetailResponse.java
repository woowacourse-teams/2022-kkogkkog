package com.woowacourse.kkogkkog.coupon.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.coupon.domain.query.CouponDetailData;
import com.woowacourse.kkogkkog.domain.MemberHistory;
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
                                LocalDateTime meetingDate,
                                List<CouponHistoryResponse> couponHistories) {
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

    public static CouponDetailResponse of(CouponDetailData couponDetail,
                                          List<MemberHistory> memberHistories) {
        List<CouponHistoryResponse> couponHistoryResponses = memberHistories.stream()
            .map(CouponHistoryResponse::of)
            .collect(Collectors.toList());

        return new CouponDetailResponse(
            couponDetail.getId(),
            couponDetail.getSenderId(),
            couponDetail.getSenderNickname(),
            couponDetail.getSenderImageUrl(),
            couponDetail.getReceiverId(),
            couponDetail.getReceiverNickname(),
            couponDetail.getReceiverImageUrl(),
            couponDetail.getHashtag(),
            couponDetail.getDescription(),
            couponDetail.getCouponType(),
            couponDetail.getCouponStatus(),
            couponDetail.getMeetingDate(),
            couponHistoryResponses
        );
    }
}
