package com.woowacourse.kkogkkog.coupon.domain.repository;

import static com.woowacourse.kkogkkog.support.fixture.domain.CouponHistoryFixture.DECLINE;
import static com.woowacourse.kkogkkog.support.fixture.domain.CouponHistoryFixture.INIT;
import static com.woowacourse.kkogkkog.support.fixture.domain.CouponHistoryFixture.REQUEST;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.RECEIVER;
import static com.woowacourse.kkogkkog.support.fixture.domain.MemberFixture.SENDER;
import static com.woowacourse.kkogkkog.support.fixture.domain.WorkspaceFixture.KKOGKKOG;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.member.domain.Workspace;
import com.woowacourse.kkogkkog.member.domain.repository.MemberRepository;
import com.woowacourse.kkogkkog.member.domain.repository.WorkspaceRepository;
import com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture;
import com.woowacourse.kkogkkog.support.repository.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class UnreadNoticeCountRepositoryTest {

    @Autowired
    private CouponHistoryRepository couponHistoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private CouponRepository couponRepository;

    private UnreadNoticeCountCacheRepository unreadNoticeCountCacheRepository;

    private Member sender;
    private Member receiver;

    @BeforeEach
    void setUp() {
        Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
        sender = memberRepository.save(SENDER.getMember(workspace));
        receiver = memberRepository.save(RECEIVER.getMember(workspace));
        unreadNoticeCountCacheRepository =
            new UnreadNoticeCountCacheRepository(couponHistoryRepository);
    }

    @Nested
    @DisplayName("get 메서드는")
    class Get {

        @Test
        @DisplayName("유효한 캐쉬가 존재하지 않는 경우 최신 데이터를 조회하여 반환한다.")
        void updateAndReadCache() {
            Coupon coupon = couponRepository.save(CouponFixture.COFFEE.getCoupon(sender, receiver));
            couponHistoryRepository.save(INIT.getCouponHistory(sender, coupon));
            couponHistoryRepository.save(REQUEST.getCouponHistory(receiver, coupon));
            couponHistoryRepository.save(DECLINE.getCouponHistory(sender, coupon));

            Long unreadCount = unreadNoticeCountCacheRepository.get(receiver);
            assertThat(unreadCount).isEqualTo(2);
        }

        @Test
        @DisplayName("유효한 캐쉬가 이미 존재하는 경우, 해당 데이터를 그대로 반환한다.")
        void readExistingCache() {
            Coupon coupon = couponRepository.save(CouponFixture.COFFEE.getCoupon(sender, receiver));
            couponHistoryRepository.save(INIT.getCouponHistory(sender, coupon));
            couponHistoryRepository.save(REQUEST.getCouponHistory(receiver, coupon));
            couponHistoryRepository.save(DECLINE.getCouponHistory(sender, coupon));

            Long actual = unreadNoticeCountCacheRepository.get(receiver);
            Long expected = unreadNoticeCountCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("increment 메서드는")
    class Increment {

        @Test
        @DisplayName("캐쉬 데이터의 값을 1만큼 증가시킨다.")
        void readAndUpdate() {
            Long previousCount = unreadNoticeCountCacheRepository.get(receiver);

            unreadNoticeCountCacheRepository.increment(receiver);
            Long actual = unreadNoticeCountCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(previousCount + 1);
        }
    }

    @Nested
    @DisplayName("reset 메서드는")
    class Reset {

        @Test
        @DisplayName("캐쉬 데이터의 값을 0으로 초기화한다.")
        void resetToZero() {
            Coupon coupon = couponRepository.save(CouponFixture.COFFEE.getCoupon(sender, receiver));
            couponHistoryRepository.save(INIT.getCouponHistory(sender, coupon));
            couponHistoryRepository.save(REQUEST.getCouponHistory(receiver, coupon));
            couponHistoryRepository.save(DECLINE.getCouponHistory(sender, coupon));
            unreadNoticeCountCacheRepository.get(receiver);

            unreadNoticeCountCacheRepository.reset(receiver);
            Long actual = unreadNoticeCountCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(0);
        }

        @Test
        @DisplayName("캐쉬 데이터가 없는 경우 0으로 초기화한다.")
        void initWithZero() {
            unreadNoticeCountCacheRepository.reset(receiver);
            Long actual = unreadNoticeCountCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(0);
        }
    }
}
