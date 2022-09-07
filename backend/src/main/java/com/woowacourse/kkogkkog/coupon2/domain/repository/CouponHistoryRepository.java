package com.woowacourse.kkogkkog.coupon2.domain.repository;

import com.woowacourse.kkogkkog.coupon2.domain.CouponHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {

    List<CouponHistory> findAllByCouponIdOrderByCreatedTimeDesc(Long couponId);
}
