package com.woowacourse.kkogkkog.coupon.application;

import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.ACCEPT;
import static com.woowacourse.kkogkkog.coupon.domain.CouponEventType.CANCEL;
import static com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture.createRequestedCoupon;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.JEONG;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.LEO;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.kkogkkog.common.exception.InvalidRequestException;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponStatusRequest;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEventType;
import com.woowacourse.kkogkkog.coupon.domain.repository.CouponRepository;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.support.common.DatabaseCleaner;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayName("동시성을 고려했을 때 CouponService의")
class ConcurrentCouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @AfterEach
    void tearDown() {
        databaseCleaner.execute();
    }

    @Disabled
    @Nested
    @DisplayName("updateStatus 메서드는")
    class UpdateStatus {

        private Member sender;
        private Member receiver;

        @BeforeEach
        void setUp() {
            Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
            sender = memberRepository.save(JEONG.getMember(workspace));
            receiver = memberRepository.save(LEO.getMember(workspace));
        }

        @Test
        @DisplayName("동일한 쿠폰에 대해 동시에 복수의 수정 요청이 들어와도 베타락을 통해 순차적으로 처리한다.")
        void exclusiveLockToPreventLostUpdate() throws Exception {
            Coupon coupon = couponRepository.save(createRequestedCoupon(sender, receiver));
            final var executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            Future<Boolean> cancelRequest = executor.submit(
                () -> runAndCheckSuccess(receiver, coupon, CANCEL));
            Future<Boolean> acceptRequest = executor.submit(
                () -> runAndCheckSuccess(sender, coupon, ACCEPT));

            List<Boolean> actual = List.of(cancelRequest.get(), acceptRequest.get());

            assertThat(actual).containsExactlyInAnyOrder(false, true);
        }

        private boolean runAndCheckSuccess(Member member,
                                           Coupon coupon,
                                           CouponEventType eventType) {
            CouponStatusRequest couponStatusRequest = new CouponStatusRequest(member.getId(),
                coupon.getId(), eventType, null, "쿠폰 이벤트 관련 메시지");
            try {
                couponService.updateStatus(couponStatusRequest);
                return true;
            } catch (InvalidRequestException e) {
                return false;
            }
        }
    }
}
