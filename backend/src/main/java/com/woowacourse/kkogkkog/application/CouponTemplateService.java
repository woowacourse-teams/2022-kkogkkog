package com.woowacourse.kkogkkog.application;

import com.woowacourse.kkogkkog.application.dto.CouponTemplateResponse;
import com.woowacourse.kkogkkog.application.dto.CouponTemplatesResponse;
import com.woowacourse.kkogkkog.domain.repository.CouponTemplateRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CouponTemplateService {

    private final CouponTemplateRepository couponTemplateRepository;

    public CouponTemplateService(CouponTemplateRepository couponTemplateRepository) {
        this.couponTemplateRepository = couponTemplateRepository;
    }

    public CouponTemplatesResponse findAll() {
        List<CouponTemplateResponse> couponTemplateResponses = couponTemplateRepository.findAll().stream()
                .map(CouponTemplateResponse::of)
                .collect(Collectors.toList());

        return new CouponTemplatesResponse(couponTemplateResponses);
    }
}
