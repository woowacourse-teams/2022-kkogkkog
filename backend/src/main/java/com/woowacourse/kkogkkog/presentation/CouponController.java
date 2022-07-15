package com.woowacourse.kkogkkog.presentation;

import com.woowacourse.kkogkkog.application.CouponService;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.presentation.dto.CouponsResponse;
import com.woowacourse.kkogkkog.presentation.dto.MyCouponsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> show(@PathVariable Long couponId) {
        CouponResponse couponResponse = couponService.findById(couponId);
        return ResponseEntity.ok(couponResponse);
    }

    @GetMapping
    public ResponseEntity<MyCouponsResponse> showAll(@LoginMember Long authMemberId) {
        MyCouponsResponse myCouponsResponse = new MyCouponsResponse(new CouponsResponse(
                couponService.findAllBySender(authMemberId),
                couponService.findAllByReceiver(authMemberId)));

        return ResponseEntity.ok(myCouponsResponse);
    }
}
