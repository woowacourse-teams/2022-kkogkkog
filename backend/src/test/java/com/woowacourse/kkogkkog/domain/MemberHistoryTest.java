package com.woowacourse.kkogkkog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberHistoryTest {

    @Test
    @DisplayName("단일 알림에 대해서 읽지 않았을 경우, 읽음으로 상태를 변경한다")
    void isRead() {
        // given
        Member sender = new Member(1L, "userId", "workspaceId", "sender", "email", "imageUrl");
        Member receiver = new Member(1L, "userId", "workspaceId", "receiver", "email", "imageUrl");
        MemberHistory memberHistory = new MemberHistory(
            1L, sender, receiver, 1L,
            CouponType.valueOf("COFFEE"), CouponEvent.INIT, LocalDate.now());

        // when
        memberHistory.updateIsRead();

        // then
        assertThat(memberHistory.isRead()).isTrue();
    }
}
