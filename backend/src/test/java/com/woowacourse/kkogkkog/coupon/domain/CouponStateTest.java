package com.woowacourse.kkogkkog.coupon.domain;

import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.ACCEPT;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.CANCEL;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.DECLINE;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.FINISH;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.REQUEST;
import static com.woowacourse.kkogkkog.coupon.domain.CouponStatus.ACCEPTED;
import static com.woowacourse.kkogkkog.coupon.domain.CouponStatus.FINISHED;
import static com.woowacourse.kkogkkog.coupon.domain.CouponStatus.READY;
import static com.woowacourse.kkogkkog.coupon.domain.CouponStatus.REQUESTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CouponState 클래스의")
class CouponStateTest {

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2022, 9, 7, 0, 0, 0);

    @Nested
    @DisplayName("changeStatus 메서드는")
    class ChangeStatus {

        @Nested
        @DisplayName("READY 상태에서")
        class Ready {

            @Test
            @DisplayName("REQUEST 이벤트인 경우 예약날짜가 추가된다.")
            void request() {
                CouponState couponState = new CouponState(READY, null);
                couponState.changeStatus(new CouponEvent(REQUEST, DATE_TIME));

                assertAll(
                    () -> assertThat(couponState.getCouponStatus()).isEqualTo(REQUESTED),
                    () -> assertThat(couponState.getMeetingDate()).isEqualTo(DATE_TIME)
                );
            }

            @Test
            @DisplayName("FINISH 이벤트인 경우 예약날짜가 없는 상태로 유지된다.")
            void finish() {
                CouponState couponState = new CouponState(READY, null);
                couponState.changeStatus(new CouponEvent(FINISH, null));

                assertAll(
                    () -> assertThat(couponState.getCouponStatus()).isEqualTo(FINISHED),
                    () -> assertThat(couponState.getMeetingDate()).isNull()
                );
            }
        }

        @Nested
        @DisplayName("REQUESTED 상태에서")
        class Requested {

            @Test
            @DisplayName("CANCEL 이벤트인 경우 예약날짜가 제거된다.")
            void cancel() {
                CouponState couponState = new CouponState(REQUESTED, DATE_TIME);
                couponState.changeStatus(new CouponEvent(CANCEL, null));

                assertAll(
                    () -> assertThat(couponState.getCouponStatus()).isEqualTo(READY),
                    () -> assertThat(couponState.getMeetingDate()).isNull()
                );
            }

            @Test
            @DisplayName("DECLINE 이벤트인 경우 예약날짜가 제거된다.")
            void decline() {
                CouponState couponState = new CouponState(REQUESTED, DATE_TIME);
                couponState.changeStatus(new CouponEvent(DECLINE, null));

                assertAll(
                    () -> assertThat(couponState.getCouponStatus()).isEqualTo(READY),
                    () -> assertThat(couponState.getMeetingDate()).isNull()
                );
            }

            @Test
            @DisplayName("ACCEPT 이벤트인 경우 예약날짜가 유지된다.")
            void accept() {
                CouponState couponState = new CouponState(REQUESTED, DATE_TIME);
                couponState.changeStatus(new CouponEvent(ACCEPT, null));

                assertAll(
                    () -> assertThat(couponState.getCouponStatus()).isEqualTo(ACCEPTED),
                    () -> assertThat(couponState.getMeetingDate()).isEqualTo(DATE_TIME)
                );
            }
        }

        @Nested
        @DisplayName("ACCEPTED 상태에서")
        class Accepted {

            @Test
            @DisplayName("FINISH 이벤트인 경우 예약날짜가 유지된다.")
            void success() {
                CouponState couponState = new CouponState(ACCEPTED, DATE_TIME);
                couponState.changeStatus(new CouponEvent(FINISH, null));

                assertAll(
                    () -> assertThat(couponState.getCouponStatus()).isEqualTo(FINISHED),
                    () -> assertThat(couponState.getMeetingDate()).isEqualTo(DATE_TIME)
                );
            }
        }
    }
}
