package com.woowacourse.kkogkkog.infrastructure.event;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.infrastructure.domain.WoowacourseUserRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture;
import com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture;
import com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
@DisplayName("PushAlarmPublisher의")
class PushAlarmPublisherTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @Mock
    private WoowacourseUserRepository woowacourseUserRepository;

    @Nested
    @DisplayName("PublishEvent 메서드는")
    class PublishEvent {

        @Test
        @DisplayName("이메일이 우아한테크코스 워크스페이스에 존재하면, 우테코 통합알림봇으로 알림을 보낸다.")
        void success_Wooteco() {
            //given
            var pushAlarmPublisher = new PushAlarmPublisher(applicationEventPublisher, woowacourseUserRepository);

            Workspace workspace = null;
            Member arthur = MemberFixture.AUTHOR.getMember(1L, workspace);
            Member jeong = MemberFixture.JEONG.getMember(2L, workspace);
            Coupon coupon = CouponFixture.COFFEE.getCoupon(arthur, jeong);
            CouponEvent couponEvent = new CouponEvent(CouponEventType.REQUEST, LocalDateTime.now());
            CouponHistory couponHistory = CouponHistory.of(arthur, coupon, couponEvent, "message");

            String userId = "userId";
            given(woowacourseUserRepository.contains(anyString())).willReturn(true);
            given(woowacourseUserRepository.getUserId(anyString())).willReturn(Optional.of(userId));

            //when
            pushAlarmPublisher.publishEvent(couponHistory);

            //then
            verify(applicationEventPublisher, atLeastOnce())
                .publishEvent(WoowacoursePushAlarmEvent.of(userId, couponHistory));
        }

        @Test
        @DisplayName("우테코 이메일이 아니고 구글로 로그인했으면, 알림을 보내지 않는다.")
        void fail_google() {
            //given
            var pushAlarmPublisher = new PushAlarmPublisher(applicationEventPublisher, woowacourseUserRepository);

            Workspace workspace = null;
            Member arthur = MemberFixture.AUTHOR.getMember(1L, workspace);
            Member jeong = MemberFixture.JEONG.getMember(2L, workspace);
            Coupon coupon = CouponFixture.COFFEE.getCoupon(arthur, jeong);
            CouponEvent couponEvent = new CouponEvent(CouponEventType.REQUEST, LocalDateTime.now());
            CouponHistory couponHistory = CouponHistory.of(arthur, coupon, couponEvent, "message");

            given(woowacourseUserRepository.contains(anyString())).willReturn(false);

            //when
            pushAlarmPublisher.publishEvent(couponHistory);

            //then
            verify(applicationEventPublisher, never()).publishEvent(any());
        }

        @Test
        @DisplayName("슬랙으로 로그인한 적이 있으면 해당 워크스페이스로 알림을 보낸다.")
        void success_anySlack() {
            //given
            var pushAlarmPublisher = new PushAlarmPublisher(applicationEventPublisher, woowacourseUserRepository);

            Workspace workspace = WorkspaceFixture.KKOGKKOG.getWorkspace();
            Member arthur = MemberFixture.AUTHOR.getMember(1L, workspace);
            Member jeong = MemberFixture.JEONG.getMember(2L, workspace);
            Coupon coupon = CouponFixture.COFFEE.getCoupon(arthur, jeong);
            CouponEvent couponEvent = new CouponEvent(CouponEventType.REQUEST, LocalDateTime.now());
            CouponHistory couponHistory = CouponHistory.of(arthur, coupon, couponEvent, "message");

            given(woowacourseUserRepository.contains(anyString())).willReturn(false);

            //when
            pushAlarmPublisher.publishEvent(couponHistory);

            //then
            verify(applicationEventPublisher, atLeastOnce()).publishEvent(PushAlarmEvent.of(couponHistory));
        }
    }
}
