package com.woowacourse.kkogkkog.support.fixture.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponResponse;
import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.presentation.dto.RegisterCouponCodeRequest;
import com.woowacourse.kkogkkog.lazycoupon.presentation.dto.LazyCouponCreateRequest;
import com.woowacourse.kkogkkog.member.domain.Member;

public class LazyCouponDtoFixture {

    public static LazyCouponCreateRequest 미등록_COFFEE_쿠폰_생성_요청(int quantity) {
        return new LazyCouponCreateRequest(
            quantity,
            "고마워요",
            "쿠폰에 대한 설명을 작성했어요",
            "COFFEE"
        );
    }

    public static LazyCouponSaveRequest 미등록_COFFEE_쿠폰_저장_요청(Long senderId, int quantity) {
        return new LazyCouponSaveRequest(
            senderId,
            quantity,
            "고마워요",
            "쿠폰에 대한 설명을 작성했어요",
            "COFFEE"
        );
    }

    public static LazyCouponResponse 미등록_COFFEE_쿠폰_응답(Long lazyCouponId,
                                                      Member sender) {
        return new LazyCouponResponse(
            lazyCouponId,
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

    public static LazyCouponResponse 미등록_COFFEE_쿠폰_응답(Long lazyCouponId,
                                                      Member sender, String couponCode) {
        return new LazyCouponResponse(
            lazyCouponId,
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

    public static LazyCouponResponse 수령한_미등록_COFFEE_쿠폰_응답(Long lazyCouponId, Long couponId,
                                                          Member sender, Member receiver) {
        return new LazyCouponResponse(
            lazyCouponId,
            "쿠폰코드",
            new CouponMemberResponse(sender.getId(), sender.getNickname(), sender.getImageUrl()),
            new CouponMemberResponse(receiver.getId(), receiver.getNickname(), receiver.getImageUrl()),
            couponId,
            "고마워요",
            "쿠폰에 대한 메시지",
            "COFFEE",
            "REGISTERED",
            null);
    }

    public static RegisterCouponCodeRequest 쿠폰_코드_등록_요청(String couponCode) {
        return new RegisterCouponCodeRequest(couponCode);
    }
}
