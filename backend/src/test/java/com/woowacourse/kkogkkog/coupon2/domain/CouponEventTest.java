package com.woowacourse.kkogkkog.coupon2.domain;

import static com.woowacourse.kkogkkog.coupon2.domain.CouponEventType.REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("CouponEvent 클래스의")
class CouponEventTest {

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2022, 9, 7, 0, 0, 0);

    @Nested
    @DisplayName("생성자는")
    class Constructor {

        @Nested
        @DisplayName("REQUEST 타입인 경우")
        class Request {

            @Test
            @DisplayName("예약날짜가 있어야 한다.")
            void success() {
                CouponEvent requestEvent = new CouponEvent(REQUEST, DATE_TIME);

                assertThat(requestEvent.getMeetingDate()).isEqualTo(DATE_TIME);
            }

            @Test
            @DisplayName("예약날짜가 없는 경우 예외가 발생한다.")
            void needsMeetingDate() {
                assertThatThrownBy(() -> new CouponEvent(REQUEST, null))
                    .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @DisplayName("REQUEST 이외의 타입인 경우, 예약날짜가 null이 된다.")
        @ParameterizedTest
        @EnumSource(value = CouponEventType.class, names = {"INIT", "CANCEL", "DECLINE", "ACCEPT", "FINISH"})
        void meetingDateAlwaysNull(CouponEventType eventType) {
            CouponEvent requestEvent = new CouponEvent(eventType, DATE_TIME);

            assertThat(requestEvent.getMeetingDate()).isNull();
        }
    }
}
