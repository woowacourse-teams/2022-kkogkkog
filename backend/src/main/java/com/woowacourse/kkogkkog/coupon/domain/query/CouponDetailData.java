package com.woowacourse.kkogkkog.coupon.domain.query;

import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponDetailData {

    private Long id;
    private Long senderId;
    private String senderNickname;
    private String senderImageUrl;
    private Long receiverId;
    private String receiverNickname;
    private String receiverImageUrl;
    private String hashtag;
    private String description;
    private CouponType couponType;
    private CouponStatus couponStatus;
    private LocalDateTime meetingDate;

    public CouponDetailData(Long id, Long senderId, String senderNickname, String senderImageUrl,
                            Long receiverId, String receiverNickname, String receiverImageUrl,
                            String hashtag, String description, CouponType couponType,
                            CouponStatus couponStatus, LocalDateTime meetingDate) {
        this.id = id;
        this.senderId = senderId;
        this.senderNickname = senderNickname;
        this.senderImageUrl = senderImageUrl;
        this.receiverId = receiverId;
        this.receiverNickname = receiverNickname;
        this.receiverImageUrl = receiverImageUrl;
        this.hashtag = hashtag;
        this.description = description;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
        this.meetingDate = meetingDate;
    }
}
