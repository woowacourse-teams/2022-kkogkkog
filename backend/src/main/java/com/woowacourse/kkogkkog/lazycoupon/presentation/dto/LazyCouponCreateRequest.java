package com.woowacourse.kkogkkog.lazycoupon.presentation.dto;

import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponSaveRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LazyCouponCreateRequest {

    private Integer quantity;
    private String couponTag;

    @Size(max = 50)
    private String couponMessage;

    @NotBlank(message = "쿠폰 타입을 입력해주세요")
    private String couponType;

    public LazyCouponCreateRequest(Integer quantity, String couponTag, String couponMessage,
                                   String couponType) {
        this.quantity = quantity;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
    }

    public LazyCouponSaveRequest toLazyCouponSaveRequest(Long senderId) {
        return new LazyCouponSaveRequest(senderId, quantity, couponTag, couponMessage, couponType);
    }
}
