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
    private String backgroundColor;
    private String modifier;
    private String message;
    private String couponType;

    public CouponSaveRequest(Long senderId, List<Long> receivers, String backgroundColor, String modifier,
                             String message, String couponType) {
        this.senderId = senderId;
        this.receivers = receivers;
        this.backgroundColor = backgroundColor;
        this.modifier = modifier;
        this.message = message;
        this.couponType = couponType;
    }
}
