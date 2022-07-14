package com.woowacourse.kkogkkog.presentation;

import com.woowacourse.kkogkkog.application.CouponService;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.application.dto.CouponsResponse;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping
    public ResponseEntity<CouponsResponse> create(@LoginMember Long authMemberId,
                                                  @RequestBody CouponCreateRequest couponCreateRequest) {
        List<CouponResponse> couponResponses = couponService.save(
                couponCreateRequest.toCouponSaveRequest(authMemberId));
        return ResponseEntity.created(null).body(new CouponsResponse(couponResponses));
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> show(@PathVariable Long couponId) {
        CouponResponse couponResponse = couponService.findById(couponId);
        return ResponseEntity.ok(couponResponse);
    }
}
