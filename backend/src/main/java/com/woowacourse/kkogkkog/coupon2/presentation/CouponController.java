package com.woowacourse.kkogkkog.coupon2.presentation;

import com.woowacourse.kkogkkog.common.presentation.LoginMemberId;
import com.woowacourse.kkogkkog.coupon2.application.CouponService;
import com.woowacourse.kkogkkog.coupon2.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon2.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon2.presentation.dto.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon2.presentation.dto.CouponUpdateRequest;
import com.woowacourse.kkogkkog.coupon2.presentation.dto.CouponsCreateResponse;
import com.woowacourse.kkogkkog.coupon2.presentation.dto.MyCouponsResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "couponController2")
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDetailResponse> show(@PathVariable Long couponId) {
        CouponDetailResponse couponDetailResponse = couponService.find(couponId);
        return ResponseEntity.ok(couponDetailResponse);
    }

    @GetMapping
    public ResponseEntity<MyCouponsResponse> showAll(@LoginMemberId Long loginMemberId) {
        MyCouponsResponse response = new MyCouponsResponse(
            couponService.findAllByReceiver(loginMemberId),
            couponService.findAllBySender(loginMemberId));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CouponsCreateResponse> save(@LoginMemberId Long loginMemberId,
                                                      @RequestBody CouponCreateRequest request) {
        List<CouponResponse> responses = couponService.save(
            request.toCouponSaveRequest(loginMemberId));
        return ResponseEntity.created(null).body(new CouponsCreateResponse(responses));
    }

    @PostMapping("/{couponId}/event")
    public ResponseEntity<CouponsCreateResponse> update(@LoginMemberId Long loginMemberId,
                                                        @PathVariable Long couponId,
                                                        @RequestBody CouponUpdateRequest request) {
        couponService.update(request.toCouponEventRequest(loginMemberId, couponId));
        return ResponseEntity.ok().build();
    }
}
