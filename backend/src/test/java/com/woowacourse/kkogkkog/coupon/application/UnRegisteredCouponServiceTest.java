package com.woowacourse.kkogkkog.coupon.application;

import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.미등록_COFFEE_쿠폰_발급_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.domain.repository.UnregisteredCouponRepository;
import com.woowacourse.kkogkkog.coupon.exception.UnregisteredCouponQuantityExcessException;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.support.application.ApplicationTest;
import com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
@DisplayName("UnRegisteredCouponService의")
public class UnRegisteredCouponServiceTest {

    @Autowired
    private UnregisteredCouponService unregisteredCouponService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private UnregisteredCouponRepository unregisteredCouponRepository;

    @Nested
    @DisplayName("save 메서드는")
    class Save {

        private Member sender;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(SENDER.getMember(workspace));
        }

        @Test
        @DisplayName("미등록 쿠폰들을 최대 5개 생성하고, 생성된 쿠폰들을 반환한다.")
        void success_saveAll() {
            UnregisteredCouponSaveRequest request = 미등록_COFFEE_쿠폰_발급_요청(sender.getId(), 5);

            List<UnregisteredCouponResponse> actual = unregisteredCouponService.save(request);

            assertThat(actual).hasSize(5);
        }

        @Test
        @DisplayName("수량이 0개 이하 또는 5개 초과이면, 예외를 던진다.")
        void fail_quantity_excess() {
            UnregisteredCouponSaveRequest request = 미등록_COFFEE_쿠폰_발급_요청(sender.getId(), 6);

            assertThatThrownBy(() -> unregisteredCouponService.save(request))
                .isInstanceOf(UnregisteredCouponQuantityExcessException.class);
        }
    }

    @Nested
    @DisplayName("findAllBySender 메서드는")
    class FindAllBySender {

        private Member sender;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(JEONG.getMember(workspace));
            unregisteredCouponRepository.save(CouponFixture.COFFEE.getUnregisteredCoupon(sender));
            unregisteredCouponRepository.save(CouponFixture.COFFEE.getUnregisteredCoupon(sender));
        }

        @Test
        @DisplayName("보낸 사람의 ID를 통해, 해당 ID로 발급한 미등록 쿠폰 리스트를 반환한다.")
        void success() {
            List<UnregisteredCouponResponse> actual = unregisteredCouponService.findAllBySender(
                sender.getId());

            List<Long> actualIds = actual.stream()
                .map(it -> it.getSender().getId())
                .collect(Collectors.toList());
            assertAll(
                () -> assertThat(actual).hasSize(2),
                () -> assertThat(actualIds).containsOnly(sender.getId())
            );
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class FindById {

        private Member sender;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(SENDER.getMember(workspace));
        }

        @Test
        @DisplayName("미등록 쿠폰 아이디를 받으면, 상세 정보를 반환한다.")
        void success() {
            List<UnregisteredCouponResponse> response = unregisteredCouponService.save(
                미등록_COFFEE_쿠폰_발급_요청(sender.getId(), 1));
            Long unregisteredCouponId = response.get(0).getId();

            var actual = unregisteredCouponService.findById(unregisteredCouponId);

            Long id = actual.getSender().getId();
            assertAll(
                () -> assertThat(id).isEqualTo(sender.getId()),
                () -> assertThat(actual.getDeleted()).isFalse());
        }
    }

    @Nested
    @DisplayName("findByCouponCode 메서드는")
    class FindByCouponCode {

        private Member sender;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(SENDER.getMember(workspace));
        }

        @Test
        @DisplayName("쿠폰코드를 받으면, 미등록 쿠폰의 상세 정보를 반환한다.")
        void success() {
            List<UnregisteredCouponResponse> response = unregisteredCouponService.save(
                미등록_COFFEE_쿠폰_발급_요청(sender.getId(), 1));
            String couponCode = response.get(0).getCouponCode();

            var actual = unregisteredCouponService.findByCouponCode(couponCode);

            assertThat(actual.getCouponCode()).isEqualTo(couponCode);
        }
    }

    @Nested
    @DisplayName("deleteByCouponCode 메서드는")
    class DeleteByCouponCode {

        private Member sender;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(SENDER.getMember(workspace));
        }

        @Test
        @DisplayName("쿠폰코드를 받으면, 미등록 쿠폰을 논리적 삭제한다.")
        void success() {
            List<UnregisteredCouponResponse> response = unregisteredCouponService.save(
                미등록_COFFEE_쿠폰_발급_요청(sender.getId(), 1));
            String couponCode = response.get(0).getCouponCode();

            unregisteredCouponService.deleteByCouponCode(couponCode);

            Boolean isPresent = unregisteredCouponRepository.findByCouponCode(couponCode)
                .isPresent();
            assertThat(isPresent).isFalse();
        }
    }
}
