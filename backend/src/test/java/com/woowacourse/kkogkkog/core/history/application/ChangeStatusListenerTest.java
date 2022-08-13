package com.woowacourse.kkogkkog.core.history.application;

import static com.woowacourse.kkogkkog.common.fixture.dto.ReservationDtoFixture.예약_저장_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.common.annotaion.ApplicationTest;
import com.woowacourse.kkogkkog.common.fixture.dto.CouponDtoFixture;
import com.woowacourse.kkogkkog.common.fixture.dto.ReservationDtoFixture;
import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.MemberHistory;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.domain.repository.MemberHistoryRepository;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.infrastructure.SlackClient;
import com.woowacourse.kkogkkog.reservation.application.ReservationService;
import com.woowacourse.kkogkkog.reservation.application.dto.ReservationSaveRequest;
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
@DisplayName("ChangeStatusListener 클래스의")
public class ChangeStatusListenerTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CouponService couponService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    WorkspaceRepository workspaceRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    MemberHistoryRepository memberHistoryRepository;
    @MockBean
    SlackClient slackClient;

    @Nested
    @DisplayName("saveHistory 메서드는")
    class SaveHistory {

        private Workspace workspace;
        private Member sender;
        private Member receiver;
        private ReservationSaveRequest reservationSaveRequest;

        @BeforeEach
        void setUp() {
            workspace = workspaceRepository.save(
                new Workspace(null, "T03LX3C5540", "workspace_name", "ACCESS_TOKEN"));
            sender = memberRepository.save(
                new Member(null, "sender", workspace, "sender", "rookie@gmail.com",
                    "https://slack"));
            receiver = memberRepository.save(
                new Member(null, "receiver1", workspace, "receiver", "rookie@gmail.com",
                    "https://slack"));
        }

        @Test
        @DisplayName("쿠폰을 생성할 때, 쿠폰 사용 내역을 기록한다.")
        void success_couponSave() {
            List<CouponResponse> response = couponService.save(
                CouponDtoFixture.COFFEE_쿠폰_저장_요청(sender.getId(), List.of(receiver.getId())));

            Long couponId = response.get(0).getId();
            List<MemberHistory> memberHistories = memberHistoryRepository.findAllByCouponIdOrderByCreatedAtDesc(
                couponId);
            assertThat(memberHistories).hasSize(1);
        }

        @Test
        @DisplayName("예약을 생성할 때, 쿠폰 사용 내역에 기록한다.")
        void success_reservationSave() {
            List<CouponResponse> save = couponService.save(
                CouponDtoFixture.COFFEE_쿠폰_저장_요청(sender.getId(), List.of(receiver.getId())));
            Long couponId = save.get(0).getId();

            reservationSaveRequest = 예약_저장_요청(receiver.getId(), couponId, LocalDateTime.now());
            reservationService.save(reservationSaveRequest);

            List<MemberHistory> memberHistories = memberHistoryRepository.findAllByCouponIdOrderByCreatedAtDesc(
                couponId);

            assertThat(memberHistories).hasSize(2);
        }

        @Test
        @DisplayName("예약 상태를 변경할 때, 쿠폰 사용 내역에 기록한다.")
        void success_reservationUpdate() {
            List<CouponResponse> save = couponService.save(
                CouponDtoFixture.COFFEE_쿠폰_저장_요청(sender.getId(), List.of(receiver.getId())));
            Long couponId = save.get(0).getId();

            reservationSaveRequest = 예약_저장_요청(receiver.getId(), couponId, LocalDateTime.now());
            reservationService.save(reservationSaveRequest);
            reservationService.update(
                ReservationDtoFixture.예약_수정_요청(sender.getId(), couponId, "ACCEPT"));

            List<MemberHistory> memberHistories = memberHistoryRepository.findAllByCouponIdOrderByCreatedAtDesc(
                couponId);

            assertThat(memberHistories).hasSize(3);
        }
    }

    @Nested
    @DisplayName("sendNotification 메서드는")
    class SendNotification {

        private Workspace workspace;
        private Member sender;
        private Member receiver;
        private ReservationSaveRequest reservationSaveRequest;

        @BeforeEach
        void setUp() {
            workspace = workspaceRepository.save(
                new Workspace(null, "T03LX3C5540", "workspace_name", "ACCESS_TOKEN"));
            sender = memberRepository.save(
                new Member(null, "sender", workspace, "sender", "rookie@gmail.com",
                    "https://slack"));
            receiver = memberRepository.save(
                new Member(null, "receiver", workspace, "receiver", "rookie@gmail.com",
                    "https://slack"));
        }

        @Test
        @DisplayName("쿠폰을 생성할 때, 슬랙 Push 알림을 보낸다.")
        void success_couponSave() {
            couponService.save(
                CouponDtoFixture.COFFEE_쿠폰_저장_요청(sender.getId(), List.of(receiver.getId())));

            Mockito.verify(slackClient)
                .requestPushAlarm(workspace.getAccessToken(), receiver.getUserId(),
                    "sender님이 커피 쿠폰을 보냈어요.");
        }

        @Test
        @DisplayName("예약을 생성할 때, 슬랙 Push 알림을 보낸다.")
        void success_reservationSave() {
            List<CouponResponse> save = couponService.save(
                CouponDtoFixture.COFFEE_쿠폰_저장_요청(sender.getId(), List.of(receiver.getId())));
            Long couponId = save.get(0).getId();

            reservationSaveRequest = 예약_저장_요청(receiver.getId(), couponId,
                LocalDateTime.now());
            reservationService.save(reservationSaveRequest);

            Mockito.verify(slackClient)
                .requestPushAlarm(workspace.getAccessToken(), sender.getUserId(),
                    "receiver님이 커피 쿠폰 사용을 요청했어요.");
        }

        @Test
        @DisplayName("예약 상태를 변경할 때, 슬랙 Push 알림을 보낸다.")
        void success_reservationUpdate() {
            List<CouponResponse> save = couponService.save(
                CouponDtoFixture.COFFEE_쿠폰_저장_요청(sender.getId(), List.of(receiver.getId())));
            Long couponId = save.get(0).getId();

            reservationSaveRequest = 예약_저장_요청(receiver.getId(), couponId,
                LocalDateTime.now());
            reservationService.save(reservationSaveRequest);
            reservationService.update(
                ReservationDtoFixture.예약_수정_요청(sender.getId(), couponId, "ACCEPT"));

            Mockito.verify(slackClient)
                .requestPushAlarm(workspace.getAccessToken(), receiver.getUserId(),
                    "sender님이 커피 쿠폰 사용을 승인했어요.");
        }
    }
}
