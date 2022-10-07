package com.woowacourse.kkogkkog.coupon.domain.repository;

import com.woowacourse.kkogkkog.coupon.domain.CouponUnregisteredCoupon;
import com.woowacourse.kkogkkog.coupon.domain.UnregisteredCoupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponUnregisteredCouponRepository extends JpaRepository<CouponUnregisteredCoupon, Long> {
}
