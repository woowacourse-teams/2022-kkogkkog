package com.woowacourse.kkogkkog.unregisteredcoupon.application;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.COFFEE;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static com.woowacourse.kkogkkog.support.fixture.dto.UnregisteredCouponDtoFixture.미등록_COFFEE_쿠폰_저장_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponResponse;
import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponSaveRequest;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCoupon;
import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponStatus;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponRepository;
import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponQuantityExcessException;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.support.application.ApplicationTest;
import com.woowacourse.kkogkkog.unregisteredcoupon.UnregisteredCouponService;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
@DisplayName("UnregisteredCouponService의")
public class UnregisteredCouponServiceTest {

    @Autowired
    private UnregisteredCouponService unregisteredCouponService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private CouponRepository couponRepository;

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
        @DisplayName("미등록 쿠폰들을 생성하고, 생성된 쿠폰들을 반환한다.")
        void success_saveAll() {
            UnregisteredCouponSaveRequest request = 미등록_COFFEE_쿠폰_저장_요청(sender.getId(), 5);

            List<UnregisteredCouponResponse> actual = unregisteredCouponService.save(request);

            assertThat(actual).hasSize(5);
        }

        @Test
        @DisplayName("발급하려는 수량이 최댓값보다 크면, 예외를 던진다.")
        void fail_quantity_excess() {
            UnregisteredCouponSaveRequest request = 미등록_COFFEE_쿠폰_저장_요청(sender.getId(), 6);

            assertThatThrownBy(() -> unregisteredCouponService.save(request))
                .isInstanceOf(UnregisteredCouponQuantityExcessException.class);
        }
    }

    @Nested
    @DisplayName("findAllBySender 메서드는")
    class FindAllBySender {

        private Member sender;
        private Member receiver;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(JEONG.getMember(workspace));
            receiver = memberRepository.save(AUTHOR.getMember(workspace));
            unregisteredCouponRepository.save(COFFEE.getUnregisteredCoupon(sender));
            unregisteredCouponRepository.save(COFFEE.getUnregisteredCoupon(sender));
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

        @Test
        @DisplayName("보낸 사람의 ID와 미등록 쿠폰의 상태를 통해, 보낸 사람의 해당 상태 미등록 쿠폰들을 반환한다.")
        void success_where_status() {
            List<UnregisteredCouponResponse> actual = unregisteredCouponService.findAllBySender(sender.getId(),
                UnregisteredCouponStatus.ISSUED.name());

            List<Long> actualIds = actual.stream()
                .map(it -> it.getSender().getId())
                .collect(Collectors.toList());
            assertAll(
                () -> assertThat(actual).hasSize(2),
                () -> assertThat(actualIds).containsOnly(sender.getId())
            );
        }

        @Test
        @DisplayName("REGISTERED 상태의 미등록 쿠폰 조회를 요청하면, 수령한 쿠폰 아이디와 받은 사람 정보도 반환한다.")
        void success_where_registered() {
            UnregisteredCoupon unregisteredCoupon = unregisteredCouponRepository.save(COFFEE.getUnregisteredCoupon(sender));
            Coupon coupon = couponRepository.save(unregisteredCoupon.registerCoupon(receiver));

            List<UnregisteredCouponResponse> responses = unregisteredCouponService.findAllBySender(sender.getId(),
                UnregisteredCouponStatus.REGISTERED.name());

            UnregisteredCouponResponse actual = responses.get(0);
            assertAll(
                () -> assertThat(actual.getCouponId()).isEqualTo(coupon.getId()),
                () -> assertThat(actual.getReceiver().getId()).isEqualTo(receiver.getId())
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
                미등록_COFFEE_쿠폰_저장_요청(sender.getId(), 1));
            Long unregisteredCouponId = response.get(0).getId();

            var actual = unregisteredCouponService.findById(sender.getId(), unregisteredCouponId);

            Long id = actual.getSender().getId();
            String unregisteredCouponStatus = actual.getUnregisteredCouponStatus();
            assertAll(
                () -> assertThat(id).isEqualTo(sender.getId()),
                () -> assertThat(unregisteredCouponStatus).isEqualTo(UnregisteredCouponStatus.ISSUED.name()));
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
                미등록_COFFEE_쿠폰_저장_요청(sender.getId(), 1));
            String couponCode = response.get(0).getCouponCode();

            var actual = unregisteredCouponService.findByCouponCode(couponCode);

            assertThat(actual.getCouponCode()).isEqualTo(couponCode);
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Delete {

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
                미등록_COFFEE_쿠폰_저장_요청(sender.getId(), 1));
            Long unregisteredCouponId = response.get(0).getId();

            unregisteredCouponService.delete(sender.getId(), unregisteredCouponId);

            Boolean isPresent = unregisteredCouponRepository.findById(unregisteredCouponId)
                .isPresent();
            assertThat(isPresent).isFalse();
        }
    }
}
