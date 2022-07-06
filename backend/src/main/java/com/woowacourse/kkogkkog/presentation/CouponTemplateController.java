package com.woowacourse.kkogkkog.presentation;

import com.woowacourse.kkogkkog.application.CouponTemplateService;
import com.woowacourse.kkogkkog.application.dto.CouponTemplatesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/templates")
public class CouponTemplateController {

    private final CouponTemplateService couponTemplateService;

    public CouponTemplateController(CouponTemplateService couponTemplateService) {
        this.couponTemplateService = couponTemplateService;
    }

    @GetMapping
    public ResponseEntity<CouponTemplatesResponse> showCouponTemplates() {
        CouponTemplatesResponse couponTemplatesResponse = couponTemplateService.findAll();

        return ResponseEntity.ok(couponTemplatesResponse);
    }
}
