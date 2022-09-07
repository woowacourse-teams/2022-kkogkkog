package com.woowacourse.kkogkkog.coupon2.domain.repository;

import com.woowacourse.kkogkkog.coupon2.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "couponRepository2")
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
