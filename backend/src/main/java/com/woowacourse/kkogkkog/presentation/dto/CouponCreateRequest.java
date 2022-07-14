package com.woowacourse.kkogkkog.presentation.dto;

import com.woowacourse.kkogkkog.application.dto.CouponSaveRequest;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponCreateRequest {

    private List<Long> receivers;
    private String modifier;
    private String message;
    private String backgroundColor;
    private String couponType;

    public CouponCreateRequest(List<Long> receivers, String modifier, String message, String backgroundColor,
                               String couponType) {
        this.receivers = receivers;
        this.modifier = modifier;
        this.message = message;
        this.backgroundColor = backgroundColor;
        this.couponType = couponType;
    }

    public CouponSaveRequest toCouponSaveRequest(Long senderId) {
        return new CouponSaveRequest(senderId, receivers, modifier, message, backgroundColor, couponType);
    }
}
