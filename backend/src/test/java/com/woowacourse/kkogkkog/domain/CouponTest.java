package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.ForbiddenException;
import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import com.woowacourse.kkogkkog.exception.coupon.SameSenderReceiverException;
import com.woowacourse.kkogkkog.fixture.MemberFixture;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class CouponTest {

    @Test
    void 쿠폰을_생성할_수_있다() {
        Member sender = MemberFixture.ROOKIE;
        Member receiver = MemberFixture.ARTHUR;

        Coupon actual = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                CouponStatus.READY);

        assertThat(actual).isNotNull();
    }

    @Test
    void 보낸_사람과_받는_사람이_같은_경우_예외를_던진다() {
        Member member = MemberFixture.ROOKIE;
        assertThatThrownBy(
                () -> new Coupon(null, member, member, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                        CouponStatus.READY)
        ).isInstanceOf(SameSenderReceiverException.class);
    }

    @Test
    void 받은_사람은_READY_상태의_쿠폰에_대한_사용_요청을_보낼_수_있다() {
        Member sender = MemberFixture.ROOKIE;
        Member receiver = MemberFixture.ARTHUR;
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                CouponStatus.READY);

        coupon.changeStatus(CouponEvent.REQUEST, receiver);

        assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.REQUESTED);
    }

    @Test
    void 보낸_사람은_쿠폰_사용_요청을_보낼_수_없다() {
        Member sender = MemberFixture.ROOKIE;
        Member receiver = MemberFixture.ARTHUR;
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                CouponStatus.READY);

        assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.REQUEST, sender))
                .isInstanceOf(ForbiddenException.class);
    }

    @Test
    void 받은_사람은_REQUESTED_상태의_쿠폰에_대한_사용_요청_취소를_보낼_수_있다() {
        Member sender = MemberFixture.ROOKIE;
        Member receiver = MemberFixture.ARTHUR;
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                CouponStatus.REQUESTED);

        coupon.changeStatus(CouponEvent.CANCEL, receiver);

        assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.READY);
    }

    @Test
    void 받은_사람은_READY_상태의_쿠폰에_대한_사용_요청_취소를_보낼_수_없다() {
        Member sender = MemberFixture.ROOKIE;
        Member receiver = MemberFixture.ARTHUR;
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                CouponStatus.READY);

        assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.CANCEL, receiver))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void 보낸_사람은_쿠폰_사용_요청_취소를_보낼_수_없다() {
        Member sender = MemberFixture.ROOKIE;
        Member receiver = MemberFixture.ARTHUR;
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                CouponStatus.REQUESTED);

        assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.CANCEL, sender))
                .isInstanceOf(ForbiddenException.class);
    }
}
