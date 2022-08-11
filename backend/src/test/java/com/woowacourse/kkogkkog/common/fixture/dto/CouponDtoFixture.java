package com.woowacourse.kkogkkog.common.fixture.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponReservationResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponCreateRequest;
import com.woowacourse.kkogkkog.domain.Member;
import java.time.LocalDateTime;
import java.util.List;

public class CouponDtoFixture {

    public static CouponCreateRequest COFFEE_쿠폰_생성_요청(List<Long> receiverIds) {
        return new CouponCreateRequest(
            receiverIds,
            "고마워요",
            "쿠폰에 대한 설명을 작성했어요",
            "COFFEE"
        );
    }

    public static CouponSaveRequest COFFEE_쿠폰_저장_요청(Long senderId, List<Long> receiverIds) {
        return new CouponSaveRequest(
            senderId,
            receiverIds,
            "고마워요",
            "쿠폰에 대한 설명을 작성했어요",
            "COFFEE"
        );
    }

    public static CouponResponse COFFEE_쿠폰_응답(Long couponId, Member sender, Member receiver) {
        return new CouponResponse(
            couponId,
            sender.getId(),
            sender.getNickname(),
            receiver.getId(),
            receiver.getNickname(),
            "고마워요",
            "쿠폰에 대한 설명을 작성했어요",
            "COFFEE",
            "READY");
    }

    public static CouponReservationResponse 쿠폰과_예약정보_응답(Long couponId, Long reservationId, Long memberId) {
        return new CouponReservationResponse(
            couponId,
            reservationId,
            memberId,
            "멤버 닉네임",
            "고마워요",
            "쿠폰에 대한 설명을 작성했어요",
            CouponType.COFFEE,
            CouponStatus.REQUESTED,
            "예약 요청 메시지를 입력할 수 있어요",
            LocalDateTime.of(2022, 1, 1, 0, 0, 0)
        );
    }
}
