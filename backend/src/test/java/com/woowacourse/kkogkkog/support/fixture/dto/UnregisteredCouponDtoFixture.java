package com.woowacourse.kkogkkog.support.fixture.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.presentation.dto.UnregisteredCouponCreateRequest;

public class UnregisteredCouponDtoFixture {

    public static UnregisteredCouponCreateRequest 미등록_COFFEE_쿠폰_생성_요청(int quantity) {
        return new UnregisteredCouponCreateRequest(
            quantity,
            "고마워요",
            "쿠폰에 대한 설명을 작성했어요",
            "COFFEE"
        );
    }

    public static UnregisteredCouponSaveRequest 미등록_COFFEE_쿠폰_저장_요청(Long senderId, int quantity) {
        return new UnregisteredCouponSaveRequest(
            senderId,
            quantity,
            "고마워요",
            "쿠폰에 대한 설명을 작성했어요",
            "COFFEE"
        );
    }
}
