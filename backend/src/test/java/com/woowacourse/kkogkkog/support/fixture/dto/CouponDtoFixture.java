package com.woowacourse.kkogkkog.support.fixture.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponHistoryResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponStatusRequest;
import com.woowacourse.kkogkkog.coupon.domain.event.CouponEventType;
import com.woowacourse.kkogkkog.coupon.domain.state.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponEventRequest;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;

public class CouponDtoFixture {

    private static final String COUPON_TAG = "고마워요";
    private static final String COUPON_MESSAGE = "쿠폰에 대한 메시지";
    private static final String COUPON_TYPE = CouponType.COFFEE.name();

    public static CouponCreateRequest COFFEE_쿠폰_생성_요청(List<Long> receiverIds) {
        return new CouponCreateRequest(receiverIds, COUPON_TAG, COUPON_MESSAGE, COUPON_TYPE);
    }

    public static CouponSaveRequest COFFEE_쿠폰_저장_요청(Long senderId, List<Long> receiverIds) {
        return new CouponSaveRequest(senderId, receiverIds, COUPON_TAG, COUPON_MESSAGE, COUPON_TYPE);
    }

    public static CouponResponse COFFEE_쿠폰_응답(Long couponId, Member sender, Member receiver) {
        return new CouponResponse(
            couponId,
            CouponMemberResponse.of(sender),
            CouponMemberResponse.of(receiver),
            COUPON_TAG,
            COUPON_MESSAGE,
            COUPON_TYPE,
            CouponStatus.READY.name(),
            null,
            null
        );
    }

    public static CouponDetailResponse 쿠폰_상세_응답(Long couponId, Member sender, Member receiver,
                                                CouponHistoryResponse couponHistoryResponse) {
        return new CouponDetailResponse(
            couponId,
            CouponMemberResponse.of(sender),
            CouponMemberResponse.of(receiver),
            COUPON_TAG,
            COUPON_MESSAGE,
            COUPON_TYPE,
            CouponStatus.READY.name(),
            null,
            List.of(couponHistoryResponse)
        );
    }

    public static CouponHistoryResponse 쿠폰_상세_내역_응답(Long historyId, Member member) {
        return new CouponHistoryResponse(
            historyId,
            member.getNickname(),
            member.getImageUrl(),
            CouponType.COFFEE,
            CouponEventType.REQUEST,
            null,
            "미팅 요청시 보낼 수 있는 메시지",
            null
        );
    }

    public static CouponEventRequest 쿠폰_이벤트_요청(String request,
                                               LocalDateTime meetingDate,
                                               String meetingMessage) {
        if (meetingDate == null) {
            return new CouponEventRequest(request, null, meetingMessage);
        }
        return new CouponEventRequest(request, meetingDate.toLocalDate(), meetingMessage);
    }

    public static CouponStatusRequest 쿠폰_상태_변경_요청(Long memberId,
                                                  Long couponId,
                                                  String eventType,
                                                  LocalDateTime meetingDate,
                                                  String meetingMessage) {
        return new CouponStatusRequest(memberId, couponId, CouponEventType.of(eventType), meetingDate, meetingMessage);
    }
}
