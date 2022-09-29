package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.coupon.domain.GuestCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestCouponRepository extends JpaRepository<GuestCoupon, Long> {
}
