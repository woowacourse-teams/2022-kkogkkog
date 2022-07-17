package com.woowacourse.kkogkkog.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponSaveRequest {

    private Long senderId;
    private List<Long> receivers;
    private String modifier;
    private String message;
    private String backgroundColor;
    private String couponType;

    public CouponSaveRequest(Long senderId, List<Long> receivers, String modifier, String message,
                             String backgroundColor, String couponType) {
        this.senderId = senderId;
        this.receivers = receivers;
        this.modifier = modifier;
        this.message = message;
        this.backgroundColor = backgroundColor;
        this.couponType = couponType;
    }
}
