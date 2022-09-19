package com.woowacourse.kkogkkog.legacy_coupon.presentation;

import com.woowacourse.kkogkkog.legacy_coupon.application.LegacyCouponService;
import com.woowacourse.kkogkkog.legacy_coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.legacy_coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.legacy_coupon.presentation.dto.CouponCreateRequest;
import com.woowacourse.kkogkkog.legacy_coupon.presentation.dto.CouponsCreateResponse;
import com.woowacourse.kkogkkog.legacy_coupon.presentation.dto.CouponsReservationResponse;
import com.woowacourse.kkogkkog.legacy_coupon.presentation.dto.MyCouponsReservationResponse;
import com.woowacourse.kkogkkog.common.presentation.LoginMemberId;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: should be @Deprecated
@RestController
@RequestMapping("/api/coupons")
public class LegacyCouponController {

    private final LegacyCouponService couponService;

    public LegacyCouponController(LegacyCouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDetailResponse> show(@PathVariable Long couponId) {
        CouponDetailResponse couponDetailResponse = couponService.find(couponId);
        return ResponseEntity.ok(couponDetailResponse);
    }

    @GetMapping
    public ResponseEntity<MyCouponsReservationResponse> showAll(@LoginMemberId Long loginMemberId) {
        MyCouponsReservationResponse response = new MyCouponsReservationResponse(
            new CouponsReservationResponse(
                couponService.findAllByReceiver(loginMemberId),
                couponService.findAllBySender(loginMemberId)));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CouponsCreateResponse> save(@LoginMemberId Long loginMemberId,
                                                      @RequestBody CouponCreateRequest request) {
        List<CouponResponse> responses = couponService.save(
            request.toCouponSaveRequest(loginMemberId));

        return ResponseEntity.created(null).body(new CouponsCreateResponse(responses));
    }
}
