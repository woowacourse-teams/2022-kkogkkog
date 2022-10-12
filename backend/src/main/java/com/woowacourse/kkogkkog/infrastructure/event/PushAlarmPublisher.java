package com.woowacourse.kkogkkog.infrastructure.event;

import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.infrastructure.domain.WoowacourseUserRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PushAlarmPublisher {

    private static final String WOOWACOURSE_ID = "TFELTJB7V";

    private final ApplicationEventPublisher publisher;
    private final WoowacourseUserRepository woowacourseUserRepository;

    public PushAlarmPublisher(ApplicationEventPublisher publisher,
                              WoowacourseUserRepository woowacourseUserRepository) {
        this.publisher = publisher;
        this.woowacourseUserRepository = woowacourseUserRepository;
    }

    public void publishEvent(CouponHistory couponHistory) {
        Member hostMember = couponHistory.getHostMember();

        if (woowacourseUserRepository.contains(hostMember.getEmail())) {
            String userId = woowacourseUserRepository.get(hostMember.getEmail());
            publisher.publishEvent(WoowacoursePushAlarmEvent.of(userId, couponHistory));
            return;
        }

        if (hostMember.getWorkspace() == null) {
            return;
        }

        publisher.publishEvent(PushAlarmEvent.of(couponHistory));
    }
}
