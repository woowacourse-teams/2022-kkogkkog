package com.woowacourse.kkogkkog.application;

import static com.woowacourse.kkogkkog.fixture.MemberFixture.NON_EXISTING_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
