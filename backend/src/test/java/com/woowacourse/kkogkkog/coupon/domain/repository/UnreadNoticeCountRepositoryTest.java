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
import com.woowacourse.kkogkkog.support.application.ApplicationTest;
import com.woowacourse.kkogkkog.support.common.RedisStorageCleaner;
import com.woowacourse.kkogkkog.support.fixture.domain.CouponFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ApplicationTest
class UnreadNoticeCountRepositoryTest {

    @Autowired
    private CouponHistoryRepository couponHistoryRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WorkspaceRepository workspaceRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private NoticeCacheRepository noticeCacheRepository;
    @Autowired
    private RedisStorageCleaner redisStorageCleaner;

    private Member sender;
    private Member receiver;

    @BeforeEach
    void setUp() {
        Workspace workspace = workspaceRepository.save(KKOGKKOG.getWorkspace());
        sender = memberRepository.save(SENDER.getMember(workspace));
        receiver = memberRepository.save(RECEIVER.getMember(workspace));
        redisStorageCleaner.execute();
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

            Long unreadCount = noticeCacheRepository.get(receiver);
            assertThat(unreadCount).isEqualTo(2);
        }

        @Test
        @DisplayName("유효한 캐쉬가 이미 존재하는 경우, 해당 데이터를 그대로 반환한다.")
        void readExistingCache() {
            Coupon coupon = couponRepository.save(CouponFixture.COFFEE.getCoupon(sender, receiver));
            couponHistoryRepository.save(INIT.getCouponHistory(sender, coupon));
            couponHistoryRepository.save(REQUEST.getCouponHistory(receiver, coupon));
            couponHistoryRepository.save(DECLINE.getCouponHistory(sender, coupon));

            Long actual = noticeCacheRepository.get(receiver);
            Long expected = noticeCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("increment 메서드는")
    class Increment {

        @Test
        @DisplayName("캐쉬 데이터의 값을 1만큼 증가시킨다.")
        void readAndUpdate() {
            Long previousCount = noticeCacheRepository.get(receiver);

            noticeCacheRepository.increment(receiver);
            Long actual = noticeCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(previousCount + 1);
        }

        @Test
        @DisplayName("캐쉬 데이터가 존재하지 않는 경우 굳이 생성 및 반영하지 않는다.")
        void noUpdateOnNoCache() {
            noticeCacheRepository.increment(receiver);
            noticeCacheRepository.increment(receiver);
            noticeCacheRepository.increment(receiver);
            Long actual = noticeCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("decrement 메서드는")
    class Decrement {

        @Test
        @DisplayName("캐쉬 데이터의 값을 1만큼 감소시킨다.")
        void readAndUpdate() {
            Coupon coupon = couponRepository.save(CouponFixture.COFFEE.getCoupon(sender, receiver));
            couponHistoryRepository.save(INIT.getCouponHistory(sender, coupon));
            couponHistoryRepository.save(REQUEST.getCouponHistory(receiver, coupon));
            couponHistoryRepository.save(DECLINE.getCouponHistory(sender, coupon));
            Long previousCount = noticeCacheRepository.get(receiver);

            noticeCacheRepository.decrement(receiver);
            Long actual = noticeCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(previousCount - 1);
        }

        @Test
        @DisplayName("캐쉬 데이터의 값이 0인 경우 감소시키지 않는다.")
        void minValueZero() {
            Long previousCount = noticeCacheRepository.get(receiver);

            noticeCacheRepository.decrement(receiver);
            Long actual = noticeCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(previousCount);
        }

        @Test
        @DisplayName("캐쉬 데이터가 존재하지 않는 경우 굳이 생성 및 반영하지 않는다.")
        void noUpdateOnNoCache() {
            Coupon coupon = couponRepository.save(CouponFixture.COFFEE.getCoupon(sender, receiver));
            couponHistoryRepository.save(INIT.getCouponHistory(sender, coupon));
            couponHistoryRepository.save(REQUEST.getCouponHistory(receiver, coupon));
            couponHistoryRepository.save(DECLINE.getCouponHistory(sender, coupon));

            noticeCacheRepository.decrement(receiver);
            noticeCacheRepository.decrement(receiver);
            Long actual = noticeCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(2);
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
            noticeCacheRepository.get(receiver);

            noticeCacheRepository.reset(receiver);
            Long actual = noticeCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(0);
        }

        @Test
        @DisplayName("캐쉬 데이터가 없는 경우 0으로 초기화한다.")
        void initWithZero() {
            noticeCacheRepository.reset(receiver);
            Long actual = noticeCacheRepository.get(receiver);

            assertThat(actual).isEqualTo(0);
        }
    }
}
