package com.woowacourse.kkogkkog.presentation;

import com.woowacourse.kkogkkog.application.CouponService2;
import com.woowacourse.kkogkkog.application.dto.CouponResponse2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService2 couponService;

    public CouponController(CouponService2 couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse2> showCoupon(@PathVariable Long couponId) {
        CouponResponse2 couponResponse = couponService.findById(couponId);
        return ResponseEntity.ok(couponResponse);
    }
}
