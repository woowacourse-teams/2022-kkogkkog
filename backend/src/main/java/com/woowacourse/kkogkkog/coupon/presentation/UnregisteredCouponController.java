package com.woowacourse.kkogkkog.coupon.presentation;

import com.woowacourse.kkogkkog.common.presentation.LoginMemberId;
import com.woowacourse.kkogkkog.coupon.application.UnregisteredCouponService;
import com.woowacourse.kkogkkog.coupon.application.dto.UnregisteredCouponResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.UnregisteredCouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.presentation.dto.UnregisteredCouponsResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/coupons/unregistered")
public class UnregisteredCouponController {

    private final UnregisteredCouponService unregisteredCouponService;

    public UnregisteredCouponController(UnregisteredCouponService unregisteredCouponService) {
        this.unregisteredCouponService = unregisteredCouponService;
    }

    @PostMapping
    public ResponseEntity<UnregisteredCouponsResponse> save(@LoginMemberId Long loginMemberId,
                                                            @RequestBody UnregisteredCouponCreateRequest request) {
        List<UnregisteredCouponResponse> responses = unregisteredCouponService.save(
            request.toUnregisteredCouponSaveRequest(loginMemberId));
        return ResponseEntity.created(null).body(new UnregisteredCouponsResponse(responses));
    }

    @GetMapping
    public ResponseEntity<UnregisteredCouponsResponse> show(@LoginMemberId Long loginMemberId) {
        List<UnregisteredCouponResponse> unregisteredCoupons = unregisteredCouponService.findAllBySender(
            loginMemberId);
        return ResponseEntity.ok(new UnregisteredCouponsResponse(unregisteredCoupons));
    }
}
