package com.woowacourse.kkogkkog.domain.repository;

import com.woowacourse.kkogkkog.domain.Coupon;
import com.woowacourse.kkogkkog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findAllBySender(Member sender);

    List<Coupon> findAllByReceiver(Member receiver);
}
