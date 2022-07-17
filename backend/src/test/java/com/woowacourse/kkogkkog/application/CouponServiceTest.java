package com.woowacourse.kkogkkog.application;

import static com.woowacourse.kkogkkog.fixture.MemberFixture.NON_EXISTING_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.kkogkkog.application.dto.CouponMemberResponse;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponSaveRequest;
import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.repository.MemberRepository;
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
    private static final Member ROOKIE = new Member(null, "rookie@gmail.com", "password1234!", "루키");
    private static final Member ARTHUR = new Member(null, "arthur@gmail.com", "password1234!", "아서");

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
            List<CouponResponse> savedCoupons = couponService.save(toCouponSaveRequest(JEONG, List.of(LEO, ROOKIE)));

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
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(ROOKIE, List.of(ARTHUR, JEONG, LEO));
            List<CouponResponse> createdCoupons = couponService.save(couponSaveRequest);

            assertThat(createdCoupons.size()).isEqualTo(3);
        }

        @Test
        @DisplayName("존재하지 않는 사용자가 쿠폰을 보내려는 경우 예외가 발생한다.")
        void save_senderNotFound() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(NON_EXISTING_MEMBER, List.of(ARTHUR, LEO));

            assertThatThrownBy(() -> couponService.save(couponSaveRequest))
                    .isInstanceOf(MemberNotFoundException.class);
        }

        @Test
        @DisplayName("존재하지 않는 사용자에게 쿠폰을 보내려는 경우 예외가 발생한다.")
        void save_receiverNotFound() {
            CouponSaveRequest couponSaveRequest = toCouponSaveRequest(JEONG, List.of(ARTHUR, NON_EXISTING_MEMBER));

            assertThatThrownBy(() -> couponService.save(couponSaveRequest))
                    .isInstanceOf(MemberNotFoundException.class);
        }
    }

    private CouponSaveRequest toCouponSaveRequest(Member sender, List<Member> receivers) {
        List<Long> receiverIds = receivers.stream()
                .map(Member::getId)
                .collect(Collectors.toList());
        return new CouponSaveRequest(sender.getId(), receiverIds, "#123456", "한턱내는", "추가 메세지", "COFFEE");
    }
}
