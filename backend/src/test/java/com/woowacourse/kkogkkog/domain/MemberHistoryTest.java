package com.woowacourse.kkogkkog.domain;

import static com.woowacourse.kkogkkog.common.fixture.domain.CouponFixture.COFFEE;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.CouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MemberHistoryTest {

    @Nested
    @DisplayName("toNoticeMessage 메서드는")
    class ToNoticeMessage {

        @Test
        @DisplayName("기록의 상대방 회원이 발생시킨 쿠폰 이벤트를 토대로 알림 메시지를 만들어 반환한다.")
        void generateString() {
            Workspace workspace = new Workspace(1L, "T03LX3C5540", "workspace_name",
                "xoxb-bot-access-token");
            Member jeong = new Member(null, "UJeong", workspace,
                "정", "jeong@gmail.com", "image");
            Member leo = new Member(null, "ULeo", workspace,
                "레오", "leothelion@gmail.com", "image");
            CouponType coffee = CouponType.COFFEE;
            Coupon coupon = COFFEE.getCoupon(jeong, leo);

            MemberHistory memberHistory = new MemberHistory(null, leo, jeong, coupon.getId(),
                coupon.getCouponType(), CouponEvent.INIT, null);

            String actual = memberHistory.toNoticeMessage();
            String expected = jeong.getNickname() + "님이 " + coffee.getDisplayName() + " 쿠폰을 보냈어요.";

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Test
    @DisplayName("단일 알림에 대해서 읽지 않았을 경우, 읽음으로 상태를 변경한다")
    void isRead() {
        // given
        Workspace workspace = new Workspace(1L, "T03LX3C5540", "workspace_name",
            "xoxb-bot-access-token");
        Member sender = new Member(1L, "userId", workspace, "sender", "email", "imageUrl");
        Member receiver = new Member(1L, "userId", workspace, "receiver", "email", "imageUrl");
        MemberHistory memberHistory = new MemberHistory(
            1L, sender, receiver, 1L,
            CouponType.valueOf("COFFEE"), CouponEvent.INIT, LocalDate.now());

        // when
        memberHistory.updateIsRead();

        // then
        assertThat(memberHistory.getIsRead()).isTrue();
    }
}
