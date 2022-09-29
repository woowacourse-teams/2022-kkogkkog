package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.HashMap;
import org.springframework.stereotype.Repository;

@Repository
public class UnreadNoticeCountCacheRepository {

    private final HashMap<Long, Long> unreadCountCache = new HashMap<>();
    private final CouponHistoryRepository couponHistoryRepository;

    public UnreadNoticeCountCacheRepository(CouponHistoryRepository couponHistoryRepository) {
        this.couponHistoryRepository = couponHistoryRepository;
    }

    public Long get(Member member) {
        Long memberId = member.getId();
        if (!unreadCountCache.containsKey(memberId)) {
            updateCache(member);
        }
        return unreadCountCache.get(memberId);
    }

    private void updateCache(Member member) {
        Long validUnreadCount = couponHistoryRepository.countByHostMemberAndIsReadFalse(member);
        unreadCountCache.put(member.getId(), validUnreadCount);
    }
}
