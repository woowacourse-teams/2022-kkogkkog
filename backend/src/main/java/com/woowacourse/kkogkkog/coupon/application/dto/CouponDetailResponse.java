package com.woowacourse.kkogkkog.coupon.application.dto;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
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
    private CouponMemberResponse sender;
    private CouponMemberResponse receiver;
    private String couponTag;
    private String couponMessage;
    private String couponType;
    private String couponStatus;
    private LocalDateTime meetingDate;
    private List<CouponHistoryResponse> couponHistories;

    public CouponDetailResponse(final Long id,
                          final CouponMemberResponse sender,
                          final CouponMemberResponse receiver,
                          final String couponTag,
                          final String couponMessage,
                          final String couponType,
                          final String couponStatus,
                          final LocalDateTime meetingDate,
                          final List<CouponHistoryResponse> couponHistories) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
        this.meetingDate = meetingDate;
        this.couponHistories = couponHistories;
    }

    public static CouponDetailResponse of(final Coupon coupon,
                                          final List<CouponHistory> couponHistories) {
        return new CouponDetailResponse(
            coupon.getId(),
            CouponMemberResponse.of(coupon.getSender()),
            CouponMemberResponse.of(coupon.getReceiver()),
            coupon.getCouponTag(),
            coupon.getCouponMessage(),
            coupon.getCouponType().name(),
            coupon.getCouponState().getCouponStatus().name(),
            coupon.getCouponState().getMeetingDate(),
            couponHistories.stream()
                .map(CouponHistoryResponse::of)
                .collect(Collectors.toList())
        );
    }
}
