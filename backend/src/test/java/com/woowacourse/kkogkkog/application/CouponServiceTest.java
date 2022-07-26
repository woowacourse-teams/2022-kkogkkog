package com.woowacourse.kkogkkog.application;

import static com.woowacourse.kkogkkog.fixture.MemberFixture.NON_EXISTING_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.application.dto.CouponChangeStatusRequest;
import com.woowacourse.kkogkkog.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.domain.CouponEvent;
import com.woowacourse.kkogkkog.domain.CouponStatus;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.exception.ForbiddenException;
import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import com.woowacourse.kkogkkog.exception.coupon.CouponNotFoundException;
import com.woowacourse.kkogkkog.exception.member.MemberNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CouponServiceTest extends ServiceTest {

    private static final Member JEONG = new Member(null, "jeong@gmail.com", "password1234!", "정");
    private static final Member LEO = new Member(null, "leo@gmail.com", "password1234!", "레오");
    private static final Member ROOKIE = new Member(null, "rookie@gmail.com", "password1234!",
        "루키");
    private static final Member ARTHUR = new Member(null, "arthur@gmail.com", "password1234!",
        "아서");

    @Autowired
    private CouponService couponService;

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

    @DisplayName("단일 쿠폰을 조회할 수 있다.")
    @Nested
    class FindByIdTest {

        @DisplayName("존재하는 쿠폰을 조회하는 경우 성공한다.")
        @Test
        void findById() {
            List<CouponResponse> savedCoupons = couponService.save(
                toCouponSaveRequest(JEONG, List.of(LEO, ROOKIE)));

            CouponResponse expected = savedCoupons.get(0);
            CouponResponse actual = couponService.findById(expected.getId());

            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        @DisplayName("존재하지 않는 쿠폰을 조회할 경우 예외가 발생한다.")
        void findById_notFound() {
            assertThatThrownBy(() -> couponService.findById(1L))
                .isInstanceOf(CouponNotFoundException.class);
        }
    }

    @DisplayName("사용자가 보낸 쿠폰들이 조회된다.")
    @Nested
    class FindBySenderTest {

        @DisplayName("조회되는 쿠폰 개수 확인")
        @Test
        void couponCount() {
            couponService.save(toCouponSaveRequest(ROOKIE, List.of(ARTHUR, JEONG, LEO)));
            couponService.save(toCouponSaveRequest(JEONG, List.of(ARTHUR, LEO)));
            List<CouponResponse> actual = couponService.findAllBySender(ROOKIE.getId());

            assertThat(actual.size()).isEqualTo(3);
        }

        @DisplayName("조회되는 쿠폰의 보낸 사람 정보 확인")
        @Test
        void senderId() {
            couponService.save(toCouponSaveRequest(ROOKIE, List.of(ARTHUR, JEONG, LEO)));
            couponService.save(toCouponSaveRequest(JEONG, List.of(ARTHUR, LEO)));
            Long senderId = ROOKIE.getId();

            List<Long> actual = couponService.findAllBySender(ROOKIE.getId())
                .stream().map(CouponResponse::getSender)
                .map(CouponMemberResponse::getId)
                .collect(Collectors.toList());
            List<Long> expected = List.of(senderId, senderId, senderId);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @DisplayName("사용자가 받은 쿠폰들이 조회된다.")
    @Nested
    class FindByReceiverTest {

        @DisplayName("조회되는 쿠폰 개수 확인")
        @Test
        void couponCount() {
            couponService.save(toCouponSaveRequest(ARTHUR, List.of(JEONG, LEO)));
            couponService.save(toCouponSaveRequest(LEO, List.of(JEONG, ARTHUR)));
            List<CouponResponse> actual = couponService.findAllByReceiver(JEONG.getId());

            assertThat(actual.size()).isEqualTo(2);
        }

        @DisplayName("조회되는 쿠폰의 받은 사람 정보 확인")
        @Test
        void receiverId() {
            couponService.save(toCouponSaveRequest(ARTHUR, List.of(JEONG, LEO)));
            couponService.save(toCouponSaveRequest(LEO, List.of(JEONG, ARTHUR)));
            Long receiverId = JEONG.getId();

            List<Long> actual = couponService.findAllByReceiver(receiverId)
                .stream().map(CouponResponse::getReceiver)
                .map(CouponMemberResponse::getId)
                .collect(Collectors.toList());
            List<Long> expected = List.of(receiverId, receiverId);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @DisplayName("복수의 쿠폰을 저장할 수 있다")
    @Nested
    class SaveTest {

        @Test
        @DisplayName("받는 사람으로 지정한 사용자들에게 동일한 내용의 쿠폰이 발급된다.")
        void save() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE,
                List.of(ARTHUR, JEONG, LEO));
            List<CouponResponse> createdCoupons = couponService.save(couponSaveRequest);

            assertThat(createdCoupons.size()).isEqualTo(3);
        }

        @Test
        @DisplayName("존재하지 않는 사용자가 쿠폰을 보내려는 경우 예외가 발생한다.")
        void save_senderNotFound() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(NON_EXISTING_MEMBER,
                List.of(ARTHUR, LEO));

            assertThatThrownBy(() -> couponService.save(couponSaveRequest))
                .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        @DisplayName("존재하지 않는 사용자에게 쿠폰을 보내려는 경우 예외가 발생한다.")
        void save_receiverNotFound() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(JEONG,
                List.of(ARTHUR, NON_EXISTING_MEMBER));

            assertThatThrownBy(() -> couponService.save(couponSaveRequest))
                .isInstanceOf(MemberNotFoundException.class);
        }
    }

    @DisplayName("쿠폰 이벤트에 따라 쿠폰의 상태를 수정할 수 있다")
    @Nested
    class ChangeStatusTest {

        @Test
        @DisplayName("받은 사람은 READY 상태의 쿠폰에 대한 사용 요청을 보낼 수 있다")
        void request() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();
            CouponChangeStatusRequest couponChangeStatusRequest = new CouponChangeStatusRequest(
                ARTHUR.getId(),
                couponId, CouponEvent.REQUEST);

            couponService.changeStatus(couponChangeStatusRequest);
            CouponResponse actual = couponService.findById(couponId);

            assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.REQUESTED.name());
        }

        @Test
        @DisplayName("보낸 사람은 쿠폰 사용 요청을 보낼 수 없다.")
        void request_senderNotAllowed() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();

            CouponChangeStatusRequest couponChangeStatusRequest = new CouponChangeStatusRequest(
                ROOKIE.getId(),
                couponId, CouponEvent.REQUEST);

            assertThatThrownBy(() -> couponService.changeStatus(couponChangeStatusRequest))
                .isInstanceOf(ForbiddenException.class);
        }

        @Test
        @DisplayName("받은 사람은 REQUESTED 상태의 쿠폰에 대한 취소 요청을 할 수 있다.")
        void cancel() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();
            CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.REQUEST);
            couponService.changeStatus(couponRequest);

            CouponChangeStatusRequest couponCancel = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.CANCEL);
            couponService.changeStatus(couponCancel);

            CouponResponse actual = couponService.findById(couponId);
            assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.READY.name());
        }

        @Test
        @DisplayName("보낸 사람은 쿠폰 사용 취소를 할 수 없다.")
        void cancel_senderNotAllowed() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();
            CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.REQUEST);
            couponService.changeStatus(couponRequest);

            CouponChangeStatusRequest couponCancel = new CouponChangeStatusRequest(ROOKIE.getId(),
                couponId, CouponEvent.CANCEL);

            assertThatThrownBy(() -> couponService.changeStatus(couponCancel))
                .isInstanceOf(ForbiddenException.class);
        }

        @Test
        @DisplayName("보낸 사람은 REQUESTED 상태의 쿠폰에 대한 사용 요청을 거절할 수 있다.")
        void decline() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();
            CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.REQUEST);
            couponService.changeStatus(couponRequest);

            CouponChangeStatusRequest couponDecline = new CouponChangeStatusRequest(ROOKIE.getId(),
                couponId, CouponEvent.DECLINE);
            couponService.changeStatus(couponDecline);

            CouponResponse actual = couponService.findById(couponId);
            assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.READY.name());
        }

        @Test
        @DisplayName("받은 사람은 쿠폰 사용 요청을 거절할 수 없다.")
        void decline_receiverNotAllowed() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();
            CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.REQUEST);
            couponService.changeStatus(couponRequest);

            CouponChangeStatusRequest couponDecline = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.DECLINE);

            assertThatThrownBy(() -> couponService.changeStatus(couponDecline))
                .isInstanceOf(ForbiddenException.class);
        }

        @Test
        @DisplayName("보낸 사람은 REQUESTED 상태의 쿠폰에 대한 사용 요청을 수락할 수 있다.")
        void accept() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();
            CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.REQUEST);
            couponService.changeStatus(couponRequest);

            CouponChangeStatusRequest couponDecline = new CouponChangeStatusRequest(ROOKIE.getId(),
                couponId, CouponEvent.ACCEPT);
            couponService.changeStatus(couponDecline);

            CouponResponse actual = couponService.findById(couponId);
            assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.ACCEPTED.name());
        }

        @Test
        @DisplayName("받은 사람은 쿠폰 사용 요청을 수락할 수 없다.")
        void accept_receiverNotAllowed() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();
            CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.REQUEST);
            couponService.changeStatus(couponRequest);

            CouponChangeStatusRequest couponDecline = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.ACCEPT);

            assertThatThrownBy(() -> couponService.changeStatus(couponDecline))
                .isInstanceOf(ForbiddenException.class);
        }

        @Test
        @DisplayName("보낸 사람은 READY 상태의 쿠폰에 대한 사용 완료를 할 수 있다.")
        void finish_ready() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();
            CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(ROOKIE.getId(),
                couponId, CouponEvent.FINISH);

            couponService.changeStatus(couponRequest);
            CouponResponse actual = couponService.findById(couponId);

            assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.FINISHED.name());
        }

        @Test
        @DisplayName("보낸 사람은 REQUESTED 상태의 쿠폰에 대한 사용 완료를 할 수 있다.")
        void finish_requested() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();
            CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.REQUEST);
            couponService.changeStatus(couponRequest);

            CouponChangeStatusRequest couponFinish = new CouponChangeStatusRequest(ROOKIE.getId(),
                couponId, CouponEvent.FINISH);
            couponService.changeStatus(couponFinish);

            CouponResponse actual = couponService.findById(couponId);
            assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.FINISHED.name());
        }

        @Test
        @DisplayName("보낸 사람은 ACCEPTED 상태의 쿠폰에 대한 사용 완료를 할 수 있다.")
        void finish_accepted() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();
            CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.REQUEST);
            couponService.changeStatus(couponRequest);

            CouponChangeStatusRequest couponAccept = new CouponChangeStatusRequest(ROOKIE.getId(),
                couponId, CouponEvent.ACCEPT);
            couponService.changeStatus(couponAccept);

            CouponChangeStatusRequest couponFinish = new CouponChangeStatusRequest(ROOKIE.getId(),
                couponId, CouponEvent.FINISH);
            couponService.changeStatus(couponFinish);

            CouponResponse actual = couponService.findById(couponId);
            assertThat(actual.getCouponStatus()).isEqualTo(CouponStatus.FINISHED.name());
        }

        @Test
        @DisplayName("보낸 사람은 FINISHED 상태의 쿠폰에 대한 사용 완료를 할 수 없다.")
        void finish_finished() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR));
            Long couponId = couponService.save(couponSaveRequest).get(0).getId();
            CouponChangeStatusRequest couponRequest = new CouponChangeStatusRequest(ARTHUR.getId(),
                couponId, CouponEvent.REQUEST);
            couponService.changeStatus(couponRequest);

            CouponChangeStatusRequest couponAccept = new CouponChangeStatusRequest(ROOKIE.getId(),
                couponId, CouponEvent.ACCEPT);
            couponService.changeStatus(couponAccept);

            CouponChangeStatusRequest couponFinish = new CouponChangeStatusRequest(ROOKIE.getId(),
                couponId, CouponEvent.FINISH);
            couponService.changeStatus(couponFinish);

            assertThatThrownBy(() -> couponService.changeStatus(couponFinish))
                .isInstanceOf(InvalidRequestException.class);
        }
    }

    private CouponSaveRequest toCouponSaveRequest(Member sender, List<Member> receivers) {
        List<Long> receiverIds = receivers.stream()
            .map(Member::getId)
            .collect(Collectors.toList());
        return new CouponSaveRequest(sender.getId(), receiverIds, "#123456", "한턱내는", "추가 메세지",
            "COFFEE");
    }
}
