package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.exception.ForbiddenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponEventTest {

    @Test
    @DisplayName("쿠폰을 받은 사람이 쿠폰 사용 요청을 보낼 수 있다.")
    void receiverCanRequest() {
        boolean isSender = false;
        boolean isReceiver = true;

        assertThatNoException()
                .isThrownBy(() -> CouponEvent.REQUEST.checkExecutable(isSender, isReceiver));
    }

    @Test
    @DisplayName("쿠폰을 보낸 사람이 쿠폰 사용 요청을 보내는 경우 예외가 발생한다.")
    void senderCanNotRequest() {
        boolean isSender = true;
        boolean isReceiver = false;

        assertThatThrownBy(() -> CouponEvent.REQUEST.checkExecutable(isSender, isReceiver))
                .isInstanceOf(ForbiddenException.class);
    }

    @Test
    @DisplayName("쿠폰을 받은 사람이 쿠폰 사용 요청을 취소할 수 있다.")
    void receiverCanCancel() {
        boolean isSender = false;
        boolean isReceiver = true;

        assertThatNoException()
                .isThrownBy(() -> CouponEvent.CANCEL.checkExecutable(isSender, isReceiver));
    }

    @Test
    @DisplayName("쿠폰을 보낸 사람이 쿠폰 사용 요청 취소를 보내는 경우 예외가 발생한다.")
    void senderCanNotCancel() {
        boolean isSender = true;
        boolean isReceiver = false;

        assertThatThrownBy(() -> CouponEvent.CANCEL.checkExecutable(isSender, isReceiver))
                .isInstanceOf(ForbiddenException.class);
    }
}
