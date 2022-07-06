package com.woowacourse.kkogkkog.domain.repository;

import com.woowacourse.kkogkkog.domain.CouponTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponTemplateRepository extends JpaRepository<CouponTemplate, Long> {
}
