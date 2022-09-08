package com.woowacourse.kkogkkog.infrastructure.event;

import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.WOOWACOURSE;

import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PushAlarmEventTest {

    @Test
    @DisplayName("워크스페이스 아이디가 Woowacourse 워크스페이스와 같으면 True를 반환한다.")
    void isWoowacourseWorkspace() {
        PushAlarmEvent pushAlarmEvent = 푸시_알림_이벤트_생성(WOOWACOURSE.getWorkspace());

        Boolean actual = pushAlarmEvent.isWoowacourseWorkspace();

        Assertions.assertThat(actual).isTrue();
    }

    private static PushAlarmEvent 푸시_알림_이벤트_생성(Workspace workspace) {
        return new PushAlarmEvent(workspace.getWorkspaceId(), workspace.getAccessToken(),
            "hostMemberId", "message", CouponEvent.REQUEST);
    }
}
