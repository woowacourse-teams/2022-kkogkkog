package com.woowacourse.kkogkkog.application;

import static com.woowacourse.kkogkkog.fixture.MemberFixture.NON_EXISTING_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.application.dto.CouponChangeStatusRequest;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.application.dto.MemberHistoryResponse;
import com.woowacourse.kkogkkog.domain.CouponEvent;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.ForbiddenException;
import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import com.woowacourse.kkogkkog.exception.coupon.CouponNotFoundException;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DisplayName("CouponService 클래스의")
public class CouponServiceTest extends ServiceTest {

    private static final Member JEONG = new Member(null, "UJeong", "T03LX3C5540", "정", "image");
    private static final Member LEO = new Member(null, "ULeo", "T03LX3C5540", "레오", "image");
    private static final Member ROOKIE = new Member(null, "URookie", "T03LX3C5540", "루키", "image");
    private static final Member ARTHUR = new Member(null, "UArthur", "T03LX3C5540", "아서", "image");

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        memberRepository.save(JEONG);
        memberRepository.save(LEO);
        memberRepository.save(ROOKIE);
        memberRepository.save(ARTHUR);
    }

    private CouponSaveRequest toCouponSaveRequest(Member sender, List<Member> receivers) {
        List<Long> receiverIds = receivers.stream()
            .map(Member::getId)
            .collect(Collectors.toList());
        return new CouponSaveRequest(sender.getId(), receiverIds, "#123456", "한턱내는", "추가 메세지",
            "COFFEE");
    }

    @Nested
    @DisplayName("findById 메서드는")
    class FindById {

        @Test
        @DisplayName("쿠폰이 존재하면, 쿠폰 응답을 반환한다.")
        void success() {
            List<CouponResponse> savedCoupons = couponService.save(
                toCouponSaveRequest(JEONG, List.of(LEO, ROOKIE)));

            CouponResponse expected = savedCoupons.get(0);
            CouponResponse actual = couponService.findById(expected.getId());

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        @DisplayName("쿠폰이 존재하지 않으면, 예외를 던진다.")
        void fail_notFound() {
            assertThatThrownBy(() -> couponService.findById(1L))
                .isInstanceOf(CouponNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("findAllBySender 메서드는")
    class FindAllBySender {

        @Test
        @DisplayName("보낸 사람의 아이디를 받으면, 해당 아이디로 보낸 쿠폰의 리스트를 반환한다.")
        void success() {
            couponService.save(toCouponSaveRequest(ROOKIE, List.of(ARTHUR, JEONG, LEO)));
            couponService.save(toCouponSaveRequest(JEONG, List.of(ARTHUR, LEO)));

            Long senderId = ROOKIE.getId();

            List<CouponResponse> actual = couponService.findAllBySender(senderId);
            List<Long> actualSender = actual.stream()
                .map(it -> it.getSender().getId())
                .collect(Collectors.toList());

            assertAll(
                () -> assertThat(actual.size()).isEqualTo(3),
                () -> assertThat(actualSender).containsOnly(senderId)
            );
        }
    }

    @Nested
    @DisplayName("findAllByReceiver 메서드는")
    class FindAllByReceiver {

        @Test
        @DisplayName("받은 사람의 아이디를 받으면, 해당 아이디로 받은 쿠폰의 리스트를 반환한다.")
        void success() {
            couponService.save(toCouponSaveRequest(ARTHUR, List.of(JEONG, LEO)));
            couponService.save(toCouponSaveRequest(LEO, List.of(JEONG, ARTHUR)));

            Long receiverId = JEONG.getId();
            List<CouponResponse> actual = couponService.findAllByReceiver(receiverId);
            List<Long> actualReceiver = actual.stream()
                .map((it -> it.getReceiver().getId()))
                .collect(Collectors.toList());

            assertAll(
                () -> assertThat(actual.size()).isEqualTo(2),
                () -> assertThat(actualReceiver).containsOnly(receiverId)
            );
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Save {

        @Test
        @DisplayName("쿠폰 정보 및 보낸 사람과 받는 사람들을 받으면, 쿠폰 생성 이벤트를 저장하고 생성된 쿠폰들을 반환한다.")
        void success() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE,
                List.of(ARTHUR, JEONG, LEO));
            List<CouponResponse> createdCoupons = couponService.save(couponSaveRequest);
            List<MemberHistoryResponse> createdHistory = memberService.findHistoryById(
                ARTHUR.getId());

            Assertions.assertAll(
                () -> assertThat(createdCoupons.size()).isEqualTo(3),
                () -> assertThat(createdHistory.size()).isEqualTo(1)
            );
        }

        @Test
        @DisplayName("보낸 사람이 존재하지 않는다면, 예외를 던진다.")
        void fail_senderNotFound() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(NON_EXISTING_MEMBER,
                List.of(ARTHUR, LEO));

            assertThatThrownBy(() -> couponService.save(couponSaveRequest))
                .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        @DisplayName("받는 사람이 존재하지 않는다면, 예외를 던진다.")
        void fail_receiverNotFound() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(JEONG,
                List.of(ARTHUR, NON_EXISTING_MEMBER));

            assertThatThrownBy(() -> couponService.save(couponSaveRequest))
                .isInstanceOf(MemberNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("changeStatus 메서드는")
    class ChangeStatusTest {

        @Nested
        @DisplayName("READY 상태의 쿠폰에")
        class Ready {

            @Test
            @DisplayName("받은 사람이 REQUEST 와 약속날짜를 보내면, 쿠폰의 상태를 REQUESTED 로 변경한다.")
            void success_request() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponChangeStatusRequest = new CouponChangeStatusRequest(
                    ARTHUR.getId(), couponId, CouponEvent.REQUEST, LocalDate.of(2022, 07, 27));

                couponService.changeStatus(couponChangeStatusRequest);
                CouponResponse actual = couponService.findById(couponId);

                List<MemberHistoryResponse> createdHistory = memberService.findHistoryById(
                    ROOKIE.getId());

                assertAll(
                    () -> assertThat(actual.getCouponStatus()).isEqualTo(
                        CouponStatus.REQUESTED.name()),
                    () -> assertThat(createdHistory.size()).isEqualTo(1)
                );
            }

            @Test
            @DisplayName("받은 사람이 REQUEST 시 약속날짜를 보내지 않았을때, 예외를 던진다.")
            void fail_noMeetingDate() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponChangeStatusRequest = new CouponChangeStatusRequest(
                    ARTHUR.getId(), couponId, CouponEvent.REQUEST, null);

                assertThatThrownBy(() -> couponService.changeStatus(couponChangeStatusRequest))
                    .isInstanceOf(InvalidRequestException.class);
            }

            @Test
            @DisplayName("보낸 사람이 FINISH 를 보내면, 쿠폰의 상태를 FINISHED 로 변경한다.")
            void success_finish() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(
                    ROOKIE.getId(),
                    couponId, CouponEvent.FINISH);

                couponService.changeStatus(couponRequest);
                CouponResponse actual = couponService.findById(couponId);

                assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.FINISHED.name());
            }

            @Test
            @DisplayName("보낸 사람이 REQUEST 를 보내면, 예외를 던진다.")
            void fail_request_senderNotAllowed() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();

                CouponChangeStatusRequest couponChangeStatusRequest = new CouponChangeStatusRequest(
                    ROOKIE.getId(),
                    couponId, CouponEvent.REQUEST);

                assertThatThrownBy(() -> couponService.changeStatus(couponChangeStatusRequest))
                    .isInstanceOf(ForbiddenException.class);
            }
        }

        @Nested
        @DisplayName("REQUESTED 상태의 쿠폰에")
        class Requested {

            @Test
            @DisplayName("받은 사람이 CANCEL 을 보내면, 쿠폰의 상태를 READY 로 변경한다.")
            void success_cancel() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.REQUEST, LocalDate.of(2022, 07, 27));
                couponService.changeStatus(couponRequest);

                CouponChangeStatusRequest couponCancel = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.CANCEL);
                couponService.changeStatus(couponCancel);

                CouponResponse actual = couponService.findById(couponId);
                assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.READY.name());
            }

            @Test
            @DisplayName("보낸 사람이 DECLINE 을 보내면, 쿠폰의 상태를 READY 로 변경한다.")
            void success_decline() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.REQUEST, LocalDate.of(2022, 07, 27));
                couponService.changeStatus(couponRequest);

                CouponChangeStatusRequest couponDecline = new CouponChangeStatusRequest(
                    ROOKIE.getId(),
                    couponId, CouponEvent.DECLINE);
                couponService.changeStatus(couponDecline);

                CouponResponse actual = couponService.findById(couponId);
                assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.READY.name());
            }

            @Test
            @DisplayName("보낸 사람이 ACCEPT 를 보내면, 쿠폰의 상태를 ACCEPTED 로 변경한다.")
            void success_accept() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.REQUEST, LocalDate.of(2022, 07, 27));
                couponService.changeStatus(couponRequest);

                CouponChangeStatusRequest couponDecline = new CouponChangeStatusRequest(
                    ROOKIE.getId(),
                    couponId, CouponEvent.ACCEPT);
                couponService.changeStatus(couponDecline);

                CouponResponse actual = couponService.findById(couponId);
                assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.ACCEPTED.name());
            }

            @Test
            @DisplayName("보낸 사람이 FINISH 를 보내면, 쿠폰의 상태를 FINISHED 로 변경한다.")
            void success_finish() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.REQUEST, LocalDate.of(2022, 07, 27));
                couponService.changeStatus(couponRequest);

                CouponChangeStatusRequest couponFinish = new CouponChangeStatusRequest(
                    ROOKIE.getId(),
                    couponId, CouponEvent.FINISH);
                couponService.changeStatus(couponFinish);

                CouponResponse actual = couponService.findById(couponId);
                assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.FINISHED.name());
            }

            @Test
            @DisplayName("보낸 사람이 CANCEL 을 보내면, 예외를 던진다.")
            void fail_cancel_senderNotAllowed() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.REQUEST, LocalDate.of(2022, 07, 27));
                couponService.changeStatus(couponRequest);

                CouponChangeStatusRequest couponCancel = new CouponChangeStatusRequest(
                    ROOKIE.getId(),
                    couponId, CouponEvent.CANCEL);

                assertThatThrownBy(() -> couponService.changeStatus(couponCancel))
                    .isInstanceOf(ForbiddenException.class);
            }

            @Test
            @DisplayName("받은 사람이 DECLINE 을 보내면, 예외를 던진다.")
            void fail_decline_receiverNotAllowed() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.REQUEST, LocalDate.of(2022, 07, 27));
                couponService.changeStatus(couponRequest);

                CouponChangeStatusRequest couponDecline = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.DECLINE);

                assertThatThrownBy(() -> couponService.changeStatus(couponDecline))
                    .isInstanceOf(ForbiddenException.class);
            }

            @Test
            @DisplayName("받은 사람이 ACCEPT 를 보내면, 예외를 던진다.")
            void fail_accept_receiverNotAllowed() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.REQUEST, LocalDate.of(2022, 07, 27));
                couponService.changeStatus(couponRequest);

                CouponChangeStatusRequest couponDecline = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.ACCEPT);

                assertThatThrownBy(() -> couponService.changeStatus(couponDecline))
                    .isInstanceOf(ForbiddenException.class);
            }
        }

        @Nested
        @DisplayName("ACCEPTED 상태의 쿠폰에")
        class Accepted {

            @Test
            @DisplayName("보낸 사람이 FINISH 를 보내면, 쿠폰의 상태를 FINISHED 로 변경한다.")
            void success_finish() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.REQUEST, LocalDate.of(2022, 07, 27));
                couponService.changeStatus(couponRequest);

                CouponChangeStatusRequest couponAccept = new CouponChangeStatusRequest(
                    ROOKIE.getId(),
                    couponId, CouponEvent.ACCEPT);
                couponService.changeStatus(couponAccept);

                CouponChangeStatusRequest couponFinish = new CouponChangeStatusRequest(
                    ROOKIE.getId(),
                    couponId, CouponEvent.FINISH);
                couponService.changeStatus(couponFinish);

                CouponResponse actual = couponService.findById(couponId);
                List<MemberHistoryResponse> senderHistory = memberService.findHistoryById(
                    ROOKIE.getId());
                List<MemberHistoryResponse> receiverHistory = memberService.findHistoryById(
                    ARTHUR.getId());

                assertAll(
                    () -> assertThat(actual.getCouponStatus()).isEqualTo(
                        CouponStatus.FINISHED.name()),
                    () -> assertThat(senderHistory.size()).isEqualTo(1),
                    () -> assertThat(receiverHistory.size()).isEqualTo(3)
                );
            }
        }

        @Nested
        @DisplayName("FINISHED 상태의 쿠폰에")
        class Finished {

            @Test
            @DisplayName("보낸 사람이 FINISH 를 보내면, 예외를 던진다.")
            void fail_finish() {
                CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
                Long couponId = couponService.save(couponSaveRequest).get(0).getId();
                CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(
                    ARTHUR.getId(),
                    couponId, CouponEvent.REQUEST, LocalDate.of(2022, 07, 27));
                couponService.changeStatus(couponRequest);

                CouponChangeStatusRequest couponAccept = new CouponChangeStatusRequest(
                    ROOKIE.getId(),
                    couponId, CouponEvent.ACCEPT);
                couponService.changeStatus(couponAccept);

                CouponChangeStatusRequest couponFinish = new CouponChangeStatusRequest(
                    ROOKIE.getId(),
                    couponId, CouponEvent.FINISH);
                couponService.changeStatus(couponFinish);

                assertThatThrownBy(() -> couponService.changeStatus(couponFinish))
                    .isInstanceOf(InvalidRequestException.class);
            }
        }
    }
}
