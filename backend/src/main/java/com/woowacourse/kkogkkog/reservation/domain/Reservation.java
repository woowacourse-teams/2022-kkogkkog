package com.woowacourse.kkogkkog.reservation.domain;

import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponEvent;
import com.woowacourse.kkogkkog.domain.BaseEntity;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(nullable = false)
    private LocalDateTime meetingDate;

    private String message;

    public Reservation(Long id,
                       Coupon coupon,
                       LocalDateTime meetingDate,
                       String message) {
        this.id = id;
        this.coupon = coupon;
        this.meetingDate = meetingDate;
        this.message = message;
    }

    public void changeCouponStatus(CouponEvent couponEvent, Member member) {
        coupon.changeStatus(couponEvent, member);
    }
}
