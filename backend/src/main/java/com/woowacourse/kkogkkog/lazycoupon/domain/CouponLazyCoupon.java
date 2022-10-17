package com.woowacourse.kkogkkog.lazycoupon.domain;

import static com.woowacourse.kkogkkog.lazycoupon.domain.LazyCouponEventType.REGISTER;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.lazycoupon.exception.LazyCouponNotAccessibleException;
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
public class CouponLazyCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "lazy_coupon_id", nullable = false)
    private LazyCoupon lazyCoupon;

    public CouponLazyCoupon(Long id, Coupon coupon, LazyCoupon lazyCoupon) {
        this.id = id;
        this.coupon = coupon;
        this.lazyCoupon = lazyCoupon;
    }

    public CouponLazyCoupon(Coupon coupon, LazyCoupon lazyCoupon) {
        this(null, coupon, lazyCoupon);
    }

    public void validateSender(Member sender) {
        if (lazyCoupon.isNotSender(sender)) {
            throw new LazyCouponNotAccessibleException();
        }
    }

    public Coupon registerCoupon(Member receiver) {
        lazyCoupon.changeStatus(REGISTER);
        updateCoupon(lazyCoupon.toCoupon(receiver));
        return this.coupon;
    }

    public void updateCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}
