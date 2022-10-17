package com.woowacourse.kkogkkog.unregisteredcoupon.presentation;

import com.woowacourse.kkogkkog.common.presentation.LoginMemberId;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.RegisterCouponCodeRequest;
import com.woowacourse.kkogkkog.unregisteredcoupon.presentation.dto.UnregisteredCouponCreateRequest;
import com.woowacourse.kkogkkog.unregisteredcoupon.presentation.dto.UnregisteredCouponsResponse;
import com.woowacourse.kkogkkog.unregisteredcoupon.application.dto.UnregisteredCouponResponse;
import com.woowacourse.kkogkkog.unregisteredcoupon.application.UnregisteredCouponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/coupons/unregistered")
public class UnregisteredCouponController {

    private final UnregisteredCouponService unregisteredCouponService;

    @GetMapping("/status")
    public ResponseEntity<UnregisteredCouponsResponse> showAll(@LoginMemberId Long loginMemberId,
                                                               @RequestParam("type") String status) {
        List<UnregisteredCouponResponse> unregisteredCoupons = unregisteredCouponService.findAllBySender(loginMemberId,
            status);
        return ResponseEntity.ok(new UnregisteredCouponsResponse(unregisteredCoupons));
    }

    @GetMapping("/{unregisteredCouponId}")
    public ResponseEntity<UnregisteredCouponResponse> showById(@LoginMemberId Long loginMemberId,
                                                               @PathVariable Long unregisteredCouponId) {
        UnregisteredCouponResponse response = unregisteredCouponService.findById(
            loginMemberId, unregisteredCouponId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code")
    public ResponseEntity<UnregisteredCouponResponse> showByCouponCode(@RequestParam String couponCode) {
        UnregisteredCouponResponse response = unregisteredCouponService.findByCouponCode(
            couponCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UnregisteredCouponsResponse> save(@LoginMemberId Long loginMemberId,
                                                            @RequestBody UnregisteredCouponCreateRequest request) {
        List<UnregisteredCouponResponse> responses = unregisteredCouponService.save(
            request.toUnregisteredCouponSaveRequest(loginMemberId));
        return ResponseEntity.created(null).body(new UnregisteredCouponsResponse(responses));
    }

    @DeleteMapping("/{unregisteredCouponId}")
    public ResponseEntity<Void> delete(@LoginMemberId Long loginMemberId,
                                       @PathVariable Long unregisteredCouponId) {
        unregisteredCouponService.delete(loginMemberId, unregisteredCouponId);
        return ResponseEntity.ok().build();
    }

}
