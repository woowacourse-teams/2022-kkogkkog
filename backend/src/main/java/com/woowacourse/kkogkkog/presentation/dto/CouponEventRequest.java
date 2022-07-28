package com.woowacourse.kkogkkog.presentation.dto;

import com.woowacourse.kkogkkog.application.dto.CouponChangeStatusRequest;
import com.woowacourse.kkogkkog.domain.CouponEvent;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponEventRequest {

    private String couponEvent;

    public CouponEventRequest(String couponEvent) {
        this.couponEvent = couponEvent;
    }

    public CouponChangeStatusRequest toCouponChangeStatusRequest(Long loginMemberId,
                                                                 Long couponId) {
        return new CouponChangeStatusRequest(loginMemberId, couponId, CouponEvent.of(couponEvent));
    }
}
