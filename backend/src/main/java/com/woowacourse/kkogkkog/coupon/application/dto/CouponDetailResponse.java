package com.woowacourse.kkogkkog.coupon.application.dto;

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
    private Long receiverId;
    private String receiverNickname;
    private String hashtag;
    private String description;
    private String couponType;
    private String couponStatus;
    private LocalDateTime meetingDate;
    private List<CouponHistoryResponse> couponHistories;

    public CouponDetailResponse(Long id, Long senderId, String senderNickname, Long receiverId,
                                String receiverNickname, String hashtag, String description,
                                String couponType, String couponStatus, LocalDateTime meetingDate,
                                List<CouponHistoryResponse> couponHistories) {
        this.id = id;
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.receiverId = receiverId;
        this.receiverNickname = receiverNickname;
        this.hashtag = hashtag;
        this.description = description;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
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
            couponDetail.getReceiverId(),
            couponDetail.getReceiverNickname(),
            couponDetail.getHashtag(),
            couponDetail.getDescription(),
            couponDetail.getCouponType().name(),
            couponDetail.getCouponStatus().name(),
            couponDetail.getMeetingDate(),
            couponHistoryResponses
        );
    }
}
