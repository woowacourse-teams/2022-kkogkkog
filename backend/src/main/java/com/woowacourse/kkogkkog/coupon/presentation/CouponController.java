package com.woowacourse.kkogkkog.coupon.presentation;

import com.woowacourse.kkogkkog.common.presentation.LoginMemberId;
import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.application.dto.AcceptedCouponResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponDetailResponse;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.AcceptedCouponsResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponEventRequest;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/coupons")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/sent")
    public ResponseEntity<CouponsResponse> showSend(@LoginMemberId Long loginMemberId) {
        List<CouponResponse> coupons = couponService.findAllBySender(loginMemberId);
        return ResponseEntity.ok(new CouponsResponse(coupons));
    }

    @GetMapping("/sent/status")
    public ResponseEntity<CouponsResponse> showSent(@LoginMemberId Long loginMemberId,
                                                    @RequestParam(name = "type") String status) {
        List<CouponResponse> coupons = couponService.findAllBySender(loginMemberId, status);
        return ResponseEntity.ok(new CouponsResponse(coupons));
    }

    @GetMapping("/received")
    public ResponseEntity<CouponsResponse> showReceived(@LoginMemberId Long loginMemberId) {
        List<CouponResponse> coupons = couponService.findAllByReceiver(loginMemberId);
        return ResponseEntity.ok(new CouponsResponse(coupons));
    }

    @GetMapping("/received/status")
    public ResponseEntity<CouponsResponse> showReceived(@LoginMemberId Long loginMemberId,
                                                        @RequestParam(name = "type") String status) {
        List<CouponResponse> coupons = couponService.findAllByReceiver(loginMemberId, status);
        return ResponseEntity.ok(new CouponsResponse(coupons));
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDetailResponse> show(@LoginMemberId Long loginMemberId,
                                                     @PathVariable Long couponId) {
        CouponDetailResponse couponDetailResponse = couponService.find(loginMemberId, couponId);
        return ResponseEntity.ok(couponDetailResponse);
    }

    @GetMapping("/accept")
    public ResponseEntity<AcceptedCouponsResponse> showAcceptedCoupons(@LoginMemberId Long loginMemberId) {
        List<AcceptedCouponResponse> acceptedCouponResponses = couponService.findAcceptedCoupons(loginMemberId);
        return ResponseEntity.ok(new AcceptedCouponsResponse(acceptedCouponResponses));
    }

    @PostMapping
    public ResponseEntity<CouponsResponse> save(@LoginMemberId Long loginMemberId,
                                                @RequestBody CouponCreateRequest request) {
        List<CouponResponse> responses = couponService.save(
            request.toCouponSaveRequest(loginMemberId));
        return ResponseEntity.created(null).body(new CouponsResponse(responses));
    }

    @PutMapping("/{couponId}/event")
    public ResponseEntity<Void> update(@LoginMemberId Long loginMemberId,
                                       @PathVariable Long couponId,
                                       @RequestBody CouponEventRequest request) {
        couponService.updateStatus(request.toCouponStatusRequest(loginMemberId, couponId));
        return ResponseEntity.noContent().build();
    }
}
