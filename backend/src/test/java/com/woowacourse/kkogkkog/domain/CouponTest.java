package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.ForbiddenException;
import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import com.woowacourse.kkogkkog.exception.coupon.SameSenderReceiverException;
import com.woowacourse.kkogkkog.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
@DisplayName("Coupon 의")
public class CouponTest {

    @Nested
    @DisplayName("생성자는")
    class Constructor {

        @Test
        @DisplayName("쿠폰의 정보를 받으면, 쿠폰을 생성한다.")
        void success() {
            Member sender = MemberFixture.ROOKIE;
            Member receiver = MemberFixture.ARTHUR;

            Coupon actual = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                    CouponStatus.READY);

            assertThat(actual).isNotNull();
        }

        @Test
        @DisplayName("보낸 사람과 받는 사람이 동일하면, 예외를 던진다.")
        void fail_sameSenderAndReceiver() {
            Member member = MemberFixture.ROOKIE;
            assertThatThrownBy(
                    () -> new Coupon(null, member, member, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                            CouponStatus.READY)
            ).isInstanceOf(SameSenderReceiverException.class);
        }
    }

    @Nested
    @DisplayName("changeStatus 메서드는")
    class ChangeStatus {

        @Nested
        @DisplayName("READY 상태의 쿠폰에")
        class Ready {

            @Test
            @DisplayName("받은 사람이 REQUEST 를 보내면, REQUESTED 로 변경한다.")
            void success_request() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                        CouponStatus.READY);

                coupon.changeStatus(CouponEvent.REQUEST, receiver);

                assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.REQUESTED);
            }

            @Test
            @DisplayName("보낸 사람이 REQUEST 를 보내면, 예외를 던진다.")
            void fail_requestBySender() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                        CouponStatus.READY);

                assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.REQUEST, sender))
                        .isInstanceOf(ForbiddenException.class);
            }

            @Test
            @DisplayName("받은 사람이 CANCEL 을 보내면, 예외를 던진다.")
            void fail_cancelReady() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                        CouponStatus.READY);

                assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.CANCEL, receiver))
                        .isInstanceOf(InvalidRequestException.class);
            }
        }

        @Nested
        @DisplayName("REQUESTED 상태의 쿠폰에")
        class Requested {

            @Test
            @DisplayName("받은 사람이 CANCEL 을 보내면, READY 로 변경한다.")
            void success_cancel() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                        CouponStatus.REQUESTED);

                coupon.changeStatus(CouponEvent.CANCEL, receiver);

                assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.READY);
            }

            @Test
            @DisplayName("보낸 사람이 CANCEL 을 보내면, 예외를 던진다.")
            void fail_cancelBySender() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223", CouponType.COFFEE,
                        CouponStatus.REQUESTED);

                assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.CANCEL, sender))
                        .isInstanceOf(ForbiddenException.class);
            }
        }
    }
}
