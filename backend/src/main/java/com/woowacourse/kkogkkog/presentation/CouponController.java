package com.woowacourse.kkogkkog.presentation;

import com.woowacourse.kkogkkog.application.CouponService;
import com.woowacourse.kkogkkog.application.dto.CouponResponse;
import com.woowacourse.kkogkkog.presentation.dto.CouponCreateRequest;
import com.woowacourse.kkogkkog.presentation.dto.CouponEventRequest;
import com.woowacourse.kkogkkog.presentation.dto.MyCouponsResponse;
import com.woowacourse.kkogkkog.presentation.dto.SuccessResponse;
import java.util.List;
import javax.validation.Valid;
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

    @GetMapping
    public ResponseEntity<MyCouponsResponse> showAll(@LoginMember Long loginMemberId) {
        MyCouponsResponse myCouponsResponse = new MyCouponsResponse(
            couponService.findAllByReceiver(loginMemberId),
            couponService.findAllBySender(loginMemberId));

        return ResponseEntity.ok(myCouponsResponse);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<List<CouponResponse>>> create(
        @LoginMember Long loginMemberId,
        @Valid @RequestBody CouponCreateRequest couponCreateRequest) {
        List<CouponResponse> couponResponses = couponService.save(
            couponCreateRequest.toCouponSaveRequest(loginMemberId));
        return ResponseEntity.created(null).body(new SuccessResponse<>(couponResponses));
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> show(@PathVariable Long couponId) {
        CouponResponse couponResponse = couponService.findById(couponId);
        return ResponseEntity.ok(couponResponse);
    }

    @PostMapping("/{couponId}/event")
    public ResponseEntity<Void> action(@LoginMember Long loginMemberId,
                                       @PathVariable Long couponId,
                                       @RequestBody CouponEventRequest couponEventRequest) {
        couponService.changeStatus(
            couponEventRequest.toCouponChangeStatusRequest(loginMemberId, couponId));
        return ResponseEntity.ok().build();
    }
}
