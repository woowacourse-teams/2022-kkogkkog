package com.woowacourse.kkogkkog.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponCreateRequest {

    private Long senderId;
    private Long receiverId;
    private String backgroundColor;
    private String modifier;
    private String message;
    private String couponType;

    public CouponCreateRequest(Long senderId, Long receiverId, String backgroundColor, String modifier, String message,
                               String couponType) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.backgroundColor = backgroundColor;
        this.modifier = modifier;
        this.message = message;
        this.couponType = couponType;
    }
}
