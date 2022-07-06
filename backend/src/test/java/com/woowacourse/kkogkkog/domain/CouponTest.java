package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.coupon.SameSenderReceiverException;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class CouponTest {

    @Test
    void 쿠폰을_생성할_수_있다() {
        Member sender = new Member(1L, "루키");
        Member receiver = new Member(2L, "아서");

        Coupon actual = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                CouponStatus.READY);

        assertThat(actual).isNotNull();
    }

    @Test
    void 보낸_사람과_받는_사람이_같은_경우_예외를_던진다() {
        Member member = new Member(1L, "이름");
        assertThatThrownBy(
                () -> new Coupon(null, member, member, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                        CouponStatus.READY)
        ).isInstanceOf(SameSenderReceiverException.class);
    }
}
