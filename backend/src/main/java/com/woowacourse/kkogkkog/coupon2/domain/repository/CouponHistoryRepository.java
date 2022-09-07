package com.woowacourse.kkogkkog.coupon2.domain.repository;

import com.woowacourse.kkogkkog.coupon2.domain.CouponHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {
}
