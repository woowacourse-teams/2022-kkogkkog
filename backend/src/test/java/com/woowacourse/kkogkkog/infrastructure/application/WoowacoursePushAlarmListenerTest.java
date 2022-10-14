package com.woowacourse.kkogkkog.infrastructure.application;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.COFFEE_쿠폰_저장_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.쿠폰_상태_변경_요청;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.infrastructure.domain.WoowacourseUserRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.support.application.ApplicationTest;
import com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture;
import com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@ApplicationTest
@ExtendWith(MockitoExtension.class)
@DisplayName("WoowacoursePushAlarmListenerTest 클래스의")
public class WoowacoursePushAlarmListenerTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponService couponService;
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private CouponRepository couponRepository;
    @MockBean
    private WoowacoursePushAlarmClient woowacoursePushAlarmClient;
    @MockBean
    private WoowacourseUserRepository woowacourseUserRepository;

    @Nested
    @DisplayName("sendNotification 메서드는")
    class SendNotification {

        private Member sender;
        private Member receiver;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(WorkspaceFixture.WOOWACOURSE.getWorkspace(1L));
            sender = memberRepository.save(MemberFixture.SENDER.getMember(workspace));
            receiver = memberRepository.save(MemberFixture.RECEIVER.getMember(workspace));
        }

        @Test
        @DisplayName("우테코 회원의 쿠폰을 생성할 때, woowacourse 워크스페이스로 슬랙 푸시 알림을 보낸다.")
        void success_couponSave() {
            //given
            given(woowacourseUserRepository.contains(anyString())).willReturn(true);
            given(woowacourseUserRepository.getUserId(anyString())).willReturn("userId");

            //when
            couponService.save(COFFEE_쿠폰_저장_요청(sender.getId(), List.of(receiver.getId())));

            //then
            verify(woowacoursePushAlarmClient, timeout(50))
                .requestPushAlarm(anyString(), anyString());
        }

        @Test
        @DisplayName("쿠폰 상태를 변경할 때, woowacourse 워크스페이스로 슬랙 푸시 알림을 보낸다.")
        void success_couponUpdate() {
            //given
            Coupon coupon = couponRepository.save(COFFEE.getCoupon(sender, receiver));
            given(woowacourseUserRepository.contains(anyString())).willReturn(true);
            given(woowacourseUserRepository.getUserId(anyString())).willReturn("userId");

            //when
            couponService.updateStatus(
                쿠폰_상태_변경_요청(receiver.getId(), coupon.getId(), "REQUEST", LocalDateTime.now(), null));

            //then
            verify(woowacoursePushAlarmClient, timeout(50))
                .requestPushAlarm(anyString(), anyString());
        }
    }
}
