package com.woowacourse.kkogkkog.coupon.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponUnregisteredCoupon {

    public CouponUnregisteredCoupon(Long id, Coupon coupon, UnregisteredCoupon unregisteredCoupon) {
        this.id = id;
        this.coupon = coupon;
        this.unregisteredCoupon = unregisteredCoupon;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Coupon coupon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UnregisteredCoupon unregisteredCoupon;
}
