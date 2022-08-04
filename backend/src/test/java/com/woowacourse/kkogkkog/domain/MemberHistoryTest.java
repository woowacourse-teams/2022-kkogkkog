package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MemberHistoryTest {

    @Nested
    @DisplayName("toNoticeMessage 메서드는")
    class ToNoticeMessage {

        @Test
        @DisplayName("기록의 상대방 회원이 발생시킨 쿠폰 이벤트를 토대로 알림 메시지를 만들어 반환한다.")
        void generateString() {
            Member jeong = new Member(null, "UJeong", "T03LX3C5540",
                "정", "jeong@gmail.com", "image");
            Member leo = new Member(null, "ULeo", "T03LX3C5540",
                "레오", "leothelion@gmail.com", "image");
            CouponType coffee = CouponType.COFFEE;
            Coupon coupon = new Coupon(1L, jeong, leo, "한턱쏘는", "추가 메세지", "#241223",
                coffee, CouponStatus.READY);

            MemberHistory memberHistory = new MemberHistory(null, leo, jeong, coupon.getId(),
                coupon.getCouponType(), CouponEvent.INIT, coupon.getMeetingDate());

            String actual = memberHistory.toNoticeMessage();
            String expected = jeong.getNickname() + "님이 " + coffee.getDisplayName() + " 쿠폰을 보냈어요.";

            assertThat(actual).isEqualTo(expected);
        }
    }
}
