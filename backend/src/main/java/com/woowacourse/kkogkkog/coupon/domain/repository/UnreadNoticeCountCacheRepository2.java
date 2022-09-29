package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.member.domain.Member;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class UnreadNoticeCountCacheRepository2 {

    private static final String keyFormat = "unreadNotice:%d";

    private final RedisTemplate<String, Long> redisTemplate;
    private final CouponHistoryRepository couponHistoryRepository;

    public UnreadNoticeCountCacheRepository2(RedisTemplate<String, Long> redisTemplate,
                                             CouponHistoryRepository couponHistoryRepository) {
        this.redisTemplate = redisTemplate;
        this.couponHistoryRepository = couponHistoryRepository;
    }

    public Long get(Member member) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long unreadCountCache = valueOperations.get(toCacheKey(member));
        if (unreadCountCache != null) {
            return unreadCountCache;
        }
        Long unreadCount = couponHistoryRepository.countByHostMemberAndIsReadFalse(member);
        valueOperations.set(toCacheKey(member), unreadCount);
        return unreadCount;
    }

    public void increment(Member member) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long unreadCount = valueOperations.get(toCacheKey(member));
        if (unreadCount == null) {
            unreadCount = couponHistoryRepository.countByHostMemberAndIsReadFalse(member);
        }
        valueOperations.set(toCacheKey(member), unreadCount + 1);
    }

    public void decrement(Member member) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        Long unreadCount = valueOperations.get(toCacheKey(member));
        if (unreadCount == null) {
            unreadCount = couponHistoryRepository.countByHostMemberAndIsReadFalse(member);
        }
        valueOperations.set(toCacheKey(member), Math.max(unreadCount - 1, 0));
    }

    public void reset(Member member) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(toCacheKey(member), 0L);
    }

    private String toCacheKey(Member member) {
        return String.format(keyFormat, member.getId());
    }
}
