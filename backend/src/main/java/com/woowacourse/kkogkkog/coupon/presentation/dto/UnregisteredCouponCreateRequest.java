package com.woowacourse.kkogkkog.coupon.presentation.dto;

import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponSaveRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UnregisteredCouponCreateRequest {

    private Integer quantity;
    private String couponTag;

    @Size(max = 50)
    private String couponMessage;

    @NotBlank(message = "쿠폰 타입을 입력해주세요")
    private String couponType;

    public UnregisteredCouponCreateRequest(Integer quantity, String couponTag, String couponMessage,
                                           String couponType) {
        this.quantity = quantity;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
    }

    public UnregisteredCouponSaveRequest toUnregisteredCouponSaveRequest(Long senderId) {
        return new UnregisteredCouponSaveRequest(
            senderId,
            quantity,
            couponTag,
            couponMessage,
            couponType);
    }
}
