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

@DisplayName("Coupon 클래스의")
public class CouponTest {

    @Nested
    @DisplayName("생성자는")
    class Constructor {

        @Test
        @DisplayName("쿠폰의 정보를 받으면, 쿠폰을 생성한다.")
        void success() {
            Member sender = MemberFixture.ROOKIE;
            Member receiver = MemberFixture.ARTHUR;

            Coupon actual = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                CouponType.COFFEE, CouponStatus.READY);

            assertThat(actual).isNotNull();
        }

        @Test
        @DisplayName("보낸 사람과 받는 사람이 동일하면, 예외를 던진다.")
        void fail_sameSenderAndReceiver() {
            Member member = MemberFixture.ROOKIE;
            assertThatThrownBy(
                () -> new Coupon(null, member, member, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.READY)
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
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.READY);

                coupon.changeStatus(CouponEvent.REQUEST, receiver);

                assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.REQUESTED);
            }

            @Test
            @DisplayName("보낸 사람이 REQUEST 를 보내면, 예외를 던진다.")
            void fail_requestBySender() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.READY);

                assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.REQUEST, sender))
                    .isInstanceOf(ForbiddenException.class);
            }

            @Test
            @DisplayName("받은 사람이 CANCEL 을 보내면, 예외를 던진다.")
            void fail_cancelReady() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.READY);

                assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.CANCEL, receiver))
                    .isInstanceOf(InvalidRequestException.class);
            }

            @Test
            @DisplayName("보낸 사람이 ACCEPT 를 보내면, 예외를 던진다.")
            void fail_accept() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.READY);

                assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.ACCEPT, sender))
                    .isInstanceOf(InvalidRequestException.class);
            }

            @Test
            @DisplayName("보낸 사람이 FINISH 를 보내면, FINISHED 로 변경한다.")
            void success_finish_bySender() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.READY);

                coupon.changeStatus(CouponEvent.FINISH, sender);

                assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.FINISHED);
            }

            @Test
            @DisplayName("받은 사람이 FINISH 를 보내면, FINISHED 로 변경한다.")
            void success_finish_byReceiver() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.READY);

                coupon.changeStatus(CouponEvent.FINISH, receiver);

                assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.FINISHED);
            }

            @Test
            @DisplayName("쿠폰과 관련없는 사람이 FINISH 를 보내면, 예외를 던진다.")
            void fail_othersNotAllowed() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Member requester = MemberFixture.JEONG;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.READY);

                assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.ACCEPT, requester))
                    .isInstanceOf(ForbiddenException.class);
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
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.REQUESTED);

                coupon.changeStatus(CouponEvent.CANCEL, receiver);

                assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.READY);
            }

            @Test
            @DisplayName("보낸 사람이 CANCEL 을 보내면, 예외를 던진다.")
            void fail_cancel_senderNotAllowed() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.REQUESTED);

                assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.CANCEL, sender))
                    .isInstanceOf(ForbiddenException.class);
            }

            @Test
            @DisplayName("보낸 사람이 ACCEPT 를 보내면, ACCEPTED 로 변경한다.")
            void success_accept() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.REQUESTED);

                coupon.changeStatus(CouponEvent.ACCEPT, sender);

                assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.ACCEPTED);
            }

            @Test
            @DisplayName("받은 사람이 ACCEPT 를 보내면, 예외를 던진다.")
            void fail_accept_receiverNotAllowed() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.REQUESTED);

                assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.ACCEPT, receiver))
                    .isInstanceOf(ForbiddenException.class);
            }

            @Test
            @DisplayName("보낸 사람이 FINISH 를 보내면, FINISHED 로 변경한다.")
            void success_finish_bySender() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.REQUESTED);

                coupon.changeStatus(CouponEvent.FINISH, sender);

                assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.FINISHED);
            }

            @Test
            @DisplayName("보낸 사람이 FINISH 를 보내면, FINISHED 로 변경한다.")
            void success_finish_byReceiver() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.REQUESTED);

                coupon.changeStatus(CouponEvent.FINISH, receiver);

                assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.FINISHED);
            }
        }

        @Nested
        @DisplayName("ACCEPTED 상태의 쿠폰에")
        class Accepted {

            @Test
            @DisplayName("보낸 사람이 FINISH 를 보내면, FINISHED 로 변경한다")
            void success_finish_bySender() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.ACCEPTED);

                coupon.changeStatus(CouponEvent.FINISH, sender);

                assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.FINISHED);
            }

            @Test
            @DisplayName("받은 사람이 FINISH 를 보내면, FINISHED 로 변경한다")
            void success_finish_byReceiver() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.ACCEPTED);

                coupon.changeStatus(CouponEvent.FINISH, receiver);

                assertThat(coupon.getCouponStatus()).isEqualTo(CouponStatus.FINISHED);
            }
        }

        @Nested
        @DisplayName("FINISHED 상태의 쿠폰에")
        class Finished {

            @Test
            @DisplayName("보낸 사람이 FINISH 를 보내면, 예외를 던진다.")
            void fail_finish_bySender() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.FINISHED);

                assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.FINISH, sender))
                    .isInstanceOf(InvalidRequestException.class);
            }

            @Test
            @DisplayName("받은 사람이 FINISH 를 보내면, 예외를 던진다.")
            void fail_finish_byReceiver() {
                Member sender = MemberFixture.ROOKIE;
                Member receiver = MemberFixture.ARTHUR;
                Coupon coupon = new Coupon(null, sender, receiver, "한턱쏘는", "추가 메세지", "#241223",
                    CouponType.COFFEE, CouponStatus.FINISHED);

                assertThatThrownBy(() -> coupon.changeStatus(CouponEvent.FINISH, receiver))
                    .isInstanceOf(InvalidRequestException.class);
            }
        }
    }
}
