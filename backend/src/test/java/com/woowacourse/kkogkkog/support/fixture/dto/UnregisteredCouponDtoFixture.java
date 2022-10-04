package com.woowacourse.kkogkkog.support.fixture.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponSaveRequest;

public class UnregisteredCouponDtoFixture {

    public static UnregisteredCouponSaveRequest 무기명_COFFEE_쿠폰_발급_요청(Long senderId, int quantity) {
        return new UnregisteredCouponSaveRequest(
            senderId,
            quantity,
            "고마워요",
            "쿠폰에 대한 설명을 작성했어요",
            "COFFEE"
        );
    }
}
