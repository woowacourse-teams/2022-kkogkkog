package com.woowacourse.kkogkkog.domain.repository;

import com.woowacourse.kkogkkog.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
