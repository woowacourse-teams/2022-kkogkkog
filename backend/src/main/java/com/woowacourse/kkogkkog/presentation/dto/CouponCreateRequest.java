package com.woowacourse.kkogkkog.presentation.dto;

import com.woowacourse.kkogkkog.application.dto.CouponSaveRequest;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponCreateRequest {

    @NotEmpty(message = "받는 사람의 아이디를 입력해주세요")
    private List<Long> receivers;

    @NotBlank(message = "수식어를 입력해주세요")
    private String modifier;

    private String message;

    @NotBlank(message = "쿠폰 배경색을 입력해주세요")
    private String backgroundColor;

    @NotBlank(message = "쿠폰 타입을 입력해주세요")
    private String couponType;

    public CouponCreateRequest(List<Long> receivers, String modifier, String message,
                               String backgroundColor,
                               String couponType) {
        this.receivers = receivers;
        this.modifier = modifier;
        this.message = message;
        this.backgroundColor = backgroundColor;
        this.couponType = couponType;
    }

    public CouponSaveRequest toCouponSaveRequest(Long senderId) {
        return new CouponSaveRequest(senderId, receivers, modifier, message, backgroundColor,
            couponType);
    }
}
