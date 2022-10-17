package com.woowacourse.kkogkkog.unregisteredcoupon.domain;

import static com.woowacourse.kkogkkog.unregisteredcoupon.domain.UnregisteredCouponEventType.REGISTER;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.unregisteredcoupon.exception.UnregisteredCouponNotAccessibleException;
import javax.persistence.CascadeType;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "unregistered_coupon_id")
    private UnregisteredCoupon unregisteredCoupon;

    public CouponUnregisteredCoupon(Long id, Coupon coupon, UnregisteredCoupon unregisteredCoupon) {
        this.id = id;
        this.coupon = coupon;
        this.unregisteredCoupon = unregisteredCoupon;
    }

    public CouponUnregisteredCoupon(Coupon coupon, UnregisteredCoupon unregisteredCoupon) {
        this(null, coupon, unregisteredCoupon);
    }

    public void validateSender(Member sender) {
        if (unregisteredCoupon.isNotSender(sender)) {
            throw new UnregisteredCouponNotAccessibleException();
        }
    }

    public void registerCoupon(Member receiver) {
        unregisteredCoupon.changeStatus(REGISTER);
        updateCoupon(unregisteredCoupon.toCoupon(receiver));
    }

    public void updateCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}
