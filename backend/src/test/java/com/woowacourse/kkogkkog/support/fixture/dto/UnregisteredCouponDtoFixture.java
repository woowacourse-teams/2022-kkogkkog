package com.woowacourse.kkogkkog.support.fixture.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.presentation.dto.UnregisteredCouponCreateRequest;
import com.woowacourse.kkogkkog.member.domain.Member;

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

    public static UnregisteredCouponResponse 미등록_COFFEE_쿠폰_응답(Long unregisteredCouponId,
                                                              Member sender) {
        return new UnregisteredCouponResponse(
            unregisteredCouponId,
            "쿠폰코드",
            new CouponMemberResponse(sender.getId(), sender.getNickname(), sender.getImageUrl()),
            null,
            null,
            "고마워요",
            "쿠폰에 대한 메시지",
            "COFFEE",
            "ISSUED",
            null);
    }

    public static UnregisteredCouponDetailResponse 미등록_COFFEE_쿠폰_상세_응답(Long unregisteredCouponId,
                                                                       Member sender) {
        return new UnregisteredCouponDetailResponse(
            unregisteredCouponId,
            "쿠폰코드",
            new CouponMemberResponse(sender.getId(), sender.getNickname(), sender.getImageUrl()),
            null,
            null,
            "고마워요",
            "쿠폰에 대한 메시지",
            "COFFEE",
            "ISSUED",
            null);
    }

    public static UnregisteredCouponDetailResponse 미등록_COFFEE_쿠폰_상세_응답(Long unregisteredCouponId,
                                                                       Member sender,
                                                                       String couponCode) {
        return new UnregisteredCouponDetailResponse(
            unregisteredCouponId,
            couponCode,
            new CouponMemberResponse(sender.getId(), sender.getNickname(), sender.getImageUrl()),
            null,
            null,
            "고마워요",
            "쿠폰에 대한 메시지",
            "COFFEE",
            "ISSUED",
            null);
    }
}
