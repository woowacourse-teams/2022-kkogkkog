package com.woowacourse.kkogkkog.lazycoupon.presentation;

import com.woowacourse.kkogkkog.common.presentation.LoginMemberId;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.RegisterCouponCodeRequest;
import com.woowacourse.kkogkkog.lazycoupon.presentation.dto.LazyCouponCreateRequest;
import com.woowacourse.kkogkkog.lazycoupon.presentation.dto.LazyCouponsResponse;
import com.woowacourse.kkogkkog.lazycoupon.application.dto.LazyCouponResponse;
import com.woowacourse.kkogkkog.lazycoupon.application.LazyCouponService;
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
@RequestMapping("/api/v2/lazy-coupons")
public class LazyCouponController {

    private final LazyCouponService lazyCouponService;

    @GetMapping("/status")
    public ResponseEntity<LazyCouponsResponse> showAll(@LoginMemberId Long loginMemberId,
                                                       @RequestParam("type") String status) {
        List<LazyCouponResponse> lazyCoupons = lazyCouponService.findAllBySender(loginMemberId, status);
        return ResponseEntity.ok(new LazyCouponsResponse(lazyCoupons));
    }

    @GetMapping("/{lazyCouponId}")
    public ResponseEntity<LazyCouponResponse> showById(@LoginMemberId Long loginMemberId,
                                                       @PathVariable Long lazyCouponId) {
        LazyCouponResponse response = lazyCouponService.findById(loginMemberId, lazyCouponId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code")
    public ResponseEntity<LazyCouponResponse> showByCouponCode(@RequestParam String couponCode) {
        LazyCouponResponse response = lazyCouponService.findByCouponCode(couponCode);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<LazyCouponsResponse> save(@LoginMemberId Long loginMemberId,
                                                    @RequestBody LazyCouponCreateRequest request) {
        List<LazyCouponResponse> responses = lazyCouponService.save(request.toLazyCouponSaveRequest(loginMemberId));
        return ResponseEntity.created(null).body(new LazyCouponsResponse(responses));
    }

    @PostMapping("/register")
    public ResponseEntity<CouponResponse> registerCouponCode(@LoginMemberId Long loginMemberId,
                                                             @RequestBody RegisterCouponCodeRequest request) {
        CouponResponse couponResponse = lazyCouponService.saveByCouponCode(loginMemberId, request);
        return ResponseEntity.created(null).body(couponResponse);
    }

    @DeleteMapping("/{lazyCouponId}")
    public ResponseEntity<Void> delete(@LoginMemberId Long loginMemberId,
                                       @PathVariable Long lazyCouponId) {
        lazyCouponService.delete(loginMemberId, lazyCouponId);
        return ResponseEntity.ok().build();
    }
}
