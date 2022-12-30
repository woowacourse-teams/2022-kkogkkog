package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.infrastructure.domain.WoowacourseUserRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.exception.MemberNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PushAlarmPublisher {

    private final ApplicationEventPublisher publisher;
    private final WoowacourseUserRepository woowacourseUserRepository;

    public PushAlarmPublisher(ApplicationEventPublisher publisher,
                              WoowacourseUserRepository woowacourseUserRepository) {
        this.publisher = publisher;
        this.woowacourseUserRepository = woowacourseUserRepository;
    }
    @Async
    public void publishEvent(CouponHistory couponHistory) {
        Member hostMember = couponHistory.getHostMember();

        if (woowacourseUserRepository.contains(hostMember.getEmail())) {
            String userId = woowacourseUserRepository.getUserId(hostMember.getEmail())
                .orElseThrow(MemberNotFoundException::new);
            publisher.publishEvent(WoowacoursePushAlarmEvent.of(userId, couponHistory));
            return;
        }
        if (hostMember.getWorkspace() == null) {
            return;
        }
        publisher.publishEvent(PushAlarmEvent.of(couponHistory));
    }
}
