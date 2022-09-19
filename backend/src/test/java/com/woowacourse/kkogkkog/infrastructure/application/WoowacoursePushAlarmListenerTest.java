package com.woowacourse.kkogkkog.infrastructure.application;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture.쿠폰_상태_변경_요청;

import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponStatusRequest;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.support.application.ApplicationTest;
import com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture;
import com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture;
import com.woowacourse.kkogkkog.support.fixture.dto.CouponDtoFixture;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@ApplicationTest
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

    @Nested
    @DisplayName("sendNotification 메서드는")
    class SendNotification {

        private Member sender;
        private Member receiver;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(
                WorkspaceFixture.WOOWACOURSE.getWorkspace(1L));
            sender = memberRepository.save(MemberFixture.SENDER.getMember(workspace));
            receiver = memberRepository.save(MemberFixture.RECEIVER.getMember(workspace));
        }

        @Test
        @DisplayName("쿠폰을 생성할 때, woowacourse 워크스페이스로 슬랙 푸시 알림을 보낸다.")
        void success_couponSave() {
            couponService.save(
                CouponDtoFixture.COFFEE_쿠폰_저장_요청(sender.getId(), List.of(receiver.getId())));

            Mockito.verify(woowacoursePushAlarmClient, Mockito.timeout(1000))
                .requestPushAlarm(receiver.getUserId(),
                    "`" + sender.getNickname() + "` 님이 `커피` 쿠폰을 *보냈어요*\uD83D\uDC4B");
        }

        @Test
        @DisplayName("쿠폰 상태를 변경할 때, woowacourse 워크스페이스로 슬랙 푸시 알림을 보낸다.")
        void success_couponUpdate() {
            Coupon coupon = couponRepository.save(COFFEE.getCoupon(sender, receiver));
            CouponStatusRequest couponStatusRequest = 쿠폰_상태_변경_요청(
                receiver.getId(), coupon.getId(), "REQUEST", LocalDateTime.now(), null);

            couponService.updateStatus(couponStatusRequest);
            Mockito.verify(woowacoursePushAlarmClient, Mockito.timeout(1000))
                .requestPushAlarm(sender.getUserId(),
                    "`" + receiver.getNickname() + "` 님이 `커피` 쿠폰 사용을 *요청했어요*🙏");
        }
    }
}
