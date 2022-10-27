package com.woowacourse.kkogkkog.lazycoupon.application;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.createCouponLazyCoupon;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.AUTHOR;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static com.woowacourse.kkogkkog.support.fixture.dto.LazyCouponDtoFixture.미등록_COFFEE_쿠폰_저장_요청;
import static com.woowacourse.kkogkkog.support.fixture.dto.LazyCouponDtoFixture.쿠폰_코드_등록_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.domain.CouponHistory;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponHistoryRepository;
import com.woowacourse.kkogkkog.coupon.presentation.dto.RegisterCouponCodeRequest;
import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponResponse;
import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponSaveRequest;
import com.woowacourse.kkogkkog.lazycoupon.domain.CouponLazyCoupon;
import com.woowacourse.kkogkkog.lazycoupon.domain.LazyCoupon;
import com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponStatus;
import com.woowacourse.kkogkkog.lazycoupon.domain.repository.CouponLazyCouponRepository;
import com.woowacourse.kkogkkog.lazycoupon.exception.LazyCouponQuantityExcessException;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.support.application.ApplicationTest;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
@DisplayName("LazyCouponService의")
public class LazyCouponServiceTest {

    @Autowired
    private LazyCouponService lazyCouponService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private CouponHistoryRepository couponHistoryRepository;

    @Autowired
    private CouponLazyCouponRepository couponLazyCouponRepository;

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
            LazyCouponSaveRequest request = 미등록_COFFEE_쿠폰_저장_요청(sender.getId(), 5);

            List<LazyCouponResponse> actual = lazyCouponService.save(request);

            assertThat(actual).hasSize(5);
        }

        @Test
        @DisplayName("발급하려는 수량이 최댓값보다 크면, 예외를 던진다.")
        void fail_quantity_excess() {
            LazyCouponSaveRequest request = 미등록_COFFEE_쿠폰_저장_요청(sender.getId(), 6);

            assertThatThrownBy(() -> lazyCouponService.save(request))
                .isInstanceOf(LazyCouponQuantityExcessException.class);
        }
    }

    @Nested
    @DisplayName("saveByCouponCode 메서드는")
    class SaveByCouponCode {

        private Member sender;
        private Member receiver;
        private LazyCoupon lazyCoupon;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(SENDER.getMember(workspace));
            receiver = memberRepository.save(RECEIVER.getMember(workspace));
            CouponLazyCoupon couponLazyCoupon = couponLazyCouponRepository.save(createCouponLazyCoupon(sender));
            lazyCoupon = couponLazyCoupon.getLazyCoupon();
        }

        @Test
        @DisplayName("쿠폰코드와 받는 사람을 받으면 쿠폰을 생성하고, 생성된 쿠폰을 반환한다.")
        void success() {
            RegisterCouponCodeRequest request = 쿠폰_코드_등록_요청(lazyCoupon.getCouponCode());

            CouponResponse couponResponse = lazyCouponService.saveByCouponCode(receiver.getId(), request);

            assertThat(couponResponse.getId()).isNotNull();
        }

        @Test
        @DisplayName("쿠폰을 생성할 때, 쿠폰 사용 내역을 기록한다.")
        void success_couponSave() {
            RegisterCouponCodeRequest request = 쿠폰_코드_등록_요청(lazyCoupon.getCouponCode());

            CouponResponse response = lazyCouponService.saveByCouponCode(receiver.getId(), request);

            Long couponId = response.getId();
            List<CouponHistory> memberHistories = couponHistoryRepository.findAllByCouponIdOrderByCreatedTimeDesc(
                couponId);
            assertThat(memberHistories).hasSize(1);
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
            couponLazyCouponRepository.save(createCouponLazyCoupon(sender));
            couponLazyCouponRepository.save(createCouponLazyCoupon(sender));
        }

        @Test
        @DisplayName("보낸 사람의 ID와 미등록 쿠폰의 상태를 통해, 보낸 사람의 해당 상태 미등록 쿠폰들을 반환한다.")
        void success_where_status() {
            List<LazyCouponResponse> actual = lazyCouponService.findAllBySender(sender.getId(),
                LazyCouponStatus.ISSUED.name());

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
            LazyCoupon lazyCoupon = couponLazyCouponRepository.save(createCouponLazyCoupon(sender))
                .getLazyCoupon();
            CouponResponse couponResponse = lazyCouponService.saveByCouponCode(receiver.getId(),
                쿠폰_코드_등록_요청(lazyCoupon.getCouponCode()));

            List<LazyCouponResponse> responses = lazyCouponService.findAllBySender(sender.getId(),
                LazyCouponStatus.REGISTERED.name());

            LazyCouponResponse actual = responses.get(0);
            assertAll(
                () -> assertThat(actual.getCouponId()).isEqualTo(couponResponse.getId()),
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
            List<LazyCouponResponse> response = lazyCouponService.save(
                미등록_COFFEE_쿠폰_저장_요청(sender.getId(), 1));
            Long lazyCouponId = response.get(0).getId();

            var actual = lazyCouponService.findById(sender.getId(), lazyCouponId);

            Long id = actual.getSender().getId();
            String lazyCouponStatus = actual.getLazyCouponStatus();
            assertAll(
                () -> assertThat(id).isEqualTo(sender.getId()),
                () -> assertThat(lazyCouponStatus).isEqualTo(LazyCouponStatus.ISSUED.name()));
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
            List<LazyCouponResponse> response = lazyCouponService.save(
                미등록_COFFEE_쿠폰_저장_요청(sender.getId(), 1));
            String couponCode = response.get(0).getCouponCode();

            var actual = lazyCouponService.findByCouponCode(couponCode);

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
            List<LazyCouponResponse> response = lazyCouponService.save(
                미등록_COFFEE_쿠폰_저장_요청(sender.getId(), 1));
            Long lazyCouponId = response.get(0).getId();

            lazyCouponService.delete(sender.getId(), lazyCouponId);

            Boolean isPresent = couponLazyCouponRepository.findById(lazyCouponId)
                .isPresent();
            assertThat(isPresent).isFalse();
        }
    }
}
