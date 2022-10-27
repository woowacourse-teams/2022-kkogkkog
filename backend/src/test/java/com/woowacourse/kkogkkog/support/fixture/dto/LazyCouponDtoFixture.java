package com.woowacourse.kkogkkog.support.fixture.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.coupon.presentation.dto.RegisterCouponCodeRequest;
import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponResponse;
import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponSaveRequest;
import com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus;
import com.woowacourse.kkogkkog.lazycoupon.presentation.dto.LazyCouponCreateRequest;
import com.woowacourse.kkogkkog.member.domain.Member;

public class LazyCouponDtoFixture {

    private static final String COUPON_TAG = "고마워요";
    private static final String COUPON_MESSAGE = "쿠폰에 대한 메시지";
    private static final String COUPON_CODE = "쿠폰코드";
    private static final String COUPON_TYPE = CouponType.COFFEE.name();
    private static final String ISSUED_LAZY_COUPON_STATUS = LazyCouponStatus.ISSUED.name();

    public static LazyCouponCreateRequest 미등록_COFFEE_쿠폰_생성_요청(int quantity) {
        return new LazyCouponCreateRequest(quantity, COUPON_TAG, COUPON_MESSAGE, COUPON_TYPE);
    }

    public static LazyCouponSaveRequest 미등록_COFFEE_쿠폰_저장_요청(Long senderId, int quantity) {
        return new LazyCouponSaveRequest(senderId, quantity, COUPON_TAG, COUPON_MESSAGE, COUPON_TYPE);
    }

    public static LazyCouponResponse 미등록_COFFEE_쿠폰_응답(Long lazyCouponId,
                                                      Member sender) {
        return new LazyCouponResponse(lazyCouponId,
            COUPON_CODE,
            toCouponMemberResponse(sender),
            null,
            null,
            COUPON_TAG,
            COUPON_MESSAGE,
            COUPON_TYPE,
            ISSUED_LAZY_COUPON_STATUS,
            null);
    }

    public static LazyCouponResponse 미등록_COFFEE_쿠폰_응답(Long lazyCouponId,
                                                      Member sender, String couponCode) {
        return new LazyCouponResponse(lazyCouponId, couponCode, toCouponMemberResponse(sender),
            null,
            null,
            COUPON_TAG,
            COUPON_MESSAGE,
            COUPON_TYPE,
            ISSUED_LAZY_COUPON_STATUS,
            null);
    }

    public static LazyCouponResponse 수령한_미등록_COFFEE_쿠폰_응답(Long lazyCouponId, Long couponId,
                                                          Member sender, Member receiver) {
        return new LazyCouponResponse(
            lazyCouponId,
            COUPON_CODE,
            toCouponMemberResponse(sender),
            toCouponMemberResponse(receiver),
            couponId,
            COUPON_TAG,
            COUPON_MESSAGE,
            COUPON_TYPE,
            LazyCouponStatus.REGISTERED.name(),
            null);
    }

    public static RegisterCouponCodeRequest 쿠폰_코드_등록_요청(String couponCode) {
        return new RegisterCouponCodeRequest(couponCode);
    }

    private static CouponMemberResponse toCouponMemberResponse(Member sender) {
        return new CouponMemberResponse(sender.getId(), sender.getNickname(), sender.getImageUrl());
    }
}
