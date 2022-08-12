package com.woowacourse.kkogkkog.coupon.presentation;

import com.woowacourse.kkogkkog.coupon.application.CouponService;
import com.woowacourse.kkogkkog.coupon.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponCreateRequest;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsCreateResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.CouponsReservationResponse;
import com.woowacourse.kkogkkog.coupon.presentation.dto.MyCouponsReservationResponse;
import com.woowacourse.kkogkkog.presentation.LoginMember;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping
    public ResponseEntity<MyCouponsReservationResponse> showAll(@LoginMember Long loginMemberId) {
        MyCouponsReservationResponse response = new MyCouponsReservationResponse(
            new CouponsReservationResponse(
                couponService.findAllByReceiver(loginMemberId),
                couponService.findAllBySender(loginMemberId)));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CouponsCreateResponse> save(@LoginMember Long loginMember,
                                                      @RequestBody CouponCreateRequest request) {
        List<CouponResponse> responses = couponService.save(
            request.toCouponSaveRequest(loginMember));

        return ResponseEntity.created(null).body(new CouponsCreateResponse(responses));
    }
}