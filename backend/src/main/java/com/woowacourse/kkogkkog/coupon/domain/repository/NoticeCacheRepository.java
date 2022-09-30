package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeCacheRepository {

    private static final String KEY_FORMAT = "unreadNoticeCount:%d";
    private static final int CACHE_VALIDITY = 600;

    private final RedisTemplate<String, Long> redisTemplate;
    private final CouponHistoryRepository couponHistoryRepository;

    public NoticeCacheRepository(RedisTemplate<String, Long> redisTemplate,
                                 CouponHistoryRepository couponHistoryRepository) {
        this.redisTemplate = redisTemplate;
        this.couponHistoryRepository = couponHistoryRepository;
    }

    public Long get(Member member) {
        Long unreadCountCache = getCache(member);
        if (unreadCountCache != null) {
            return unreadCountCache;
        }
        Long unreadCount = couponHistoryRepository.countByHostMemberAndIsReadFalse(member);
        setCache(member, unreadCount);
        return unreadCount;
    }

    public void increment(Member member) {
        Long unreadCount = getCache(member);
        if (unreadCount != null) {
            setCache(member, unreadCount + 1);
        }
    }

    public void decrement(Member member) {
        Long unreadCount = getCache(member);
        if (unreadCount != null && unreadCount > 0) {
            setCache(member, unreadCount - 1);
        }
    }

    public void reset(Member member) {
        setCache(member, 0L);
    }

    private Long getCache(Member member) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        String cacheKey = String.format(KEY_FORMAT, member.getId());
        return valueOperations.get(cacheKey);
    }

    private void setCache(Member member, Long unreadCount) {
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        String cacheKey = String.format(KEY_FORMAT, member.getId());
        valueOperations.set(cacheKey, unreadCount, CACHE_VALIDITY, TimeUnit.SECONDS);
    }
}
