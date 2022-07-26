package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.ForbiddenException;
import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import com.woowacourse.kkogkkog.exception.coupon.SameSenderReceiverException;
import com.woowacourse.kkogkkog.fixture.MemberFixture;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    @Test
    void 보낸_사람은_REQUESTED_상태의_쿠폰에_대한_사용_요청_수락을_보낼_수_있다() {
        Member sender = MemberFixture.ROOKIE;
        Member receiver = MemberFixture.ARTHUR;
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                CouponStatus.REQUESTED);

        coupon.changeStatus(CouponEvent.ACCEPT, sender);

        assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.ACCEPTED);
    }

    @Test
    void 보낸_사람은_READY_상태의_쿠폰에_대한_사용_요청_수락을_보낼_수_없다() {
        Member sender = MemberFixture.ROOKIE;
        Member receiver = MemberFixture.ARTHUR;
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                CouponStatus.READY);

        assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.ACCEPT, sender))
                .isInstanceOf(InvalidRequestException.class);
    }

    @Test
    void 받은_사람은_쿠폰_사용_요청_수락을_보낼_수_없다() {
        Member sender = MemberFixture.ROOKIE;
        Member receiver = MemberFixture.ARTHUR;
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                CouponStatus.REQUESTED);

        assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.ACCEPT, receiver))
                .isInstanceOf(ForbiddenException.class);
    }

    @ParameterizedTest
    @MethodSource("provideSenderAndReceiver")
    void READY_상태의_쿠폰에_대한_사용_완료를_보낼_수_있다(Member requester) {
        Member sender = MemberFixture.ROOKIE;
        Member receiver = MemberFixture.ARTHUR;
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
            CouponStatus.READY);

        coupon.changeStatus(CouponEvent.FINISH, requester);

        assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.FINISHED);
    }

    @ParameterizedTest
    @MethodSource("provideSenderAndReceiver")
    void REQUESTED_상태의_쿠폰에_대한_사용_완료를_보낼_수_있다(Member requester) {
        Member sender = MemberFixture.ROOKIE;
        Member receiver = MemberFixture.ARTHUR;
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
            CouponStatus.REQUESTED);

        coupon.changeStatus(CouponEvent.FINISH, requester);

        assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.FINISHED);
    }

    @ParameterizedTest
    @MethodSource("provideSenderAndReceiver")
    void ACCEPTED_상태의_쿠폰에_대한_사용_완료를_보낼_수_있다(Member sender, Member receiver, Member requester) {
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
            CouponStatus.ACCEPTED);

        coupon.changeStatus(CouponEvent.FINISH, requester);

        assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.FINISHED);
    }

    @ParameterizedTest
    @MethodSource("provideSenderAndReceiver")
    void FINISHED_상태의_쿠폰에_대한_사용_완료를_보낼_수_없다(Member sender, Member receiver, Member requester) {
        Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
            CouponStatus.FINISHED);

        assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.FINISH, requester))
            .isInstanceOf(InvalidRequestException.class);
    }

    public static Stream<Arguments> provideSenderAndReceiver() {
        return Stream.of(
            Arguments.of(MemberFixture.ROOKIE,MemberFixture.ARTHUR,MemberFixture.ROOKIE),
            Arguments.of(MemberFixture.ROOKIE,MemberFixture.ARTHUR,MemberFixture.ARTHUR)
        );
    }
}
