package com.woowacourse.kkogkkog.domain;

import com.woowacourse.kkogkkog.exception.SameSenderReceiverException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_member_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_member_id")
    private Member receiver;

    @Column(nullable = false)
    private String modifier;

    private String message;

    @Column(nullable = false)
    private String backgroundColor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType couponType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus couponStatus;

    public Coupon(Member sender, Member receiver, String modifier, String message, String backgroundColor,
                  CouponType couponType, CouponStatus couponStatus) {
        this(null, sender, receiver, modifier, message, backgroundColor, couponType, couponStatus);
    }

    public Coupon(Long id, Member sender, Member receiver, String modifier, String message,
                  String backgroundColor, CouponType couponType, CouponStatus couponStatus) {
        validateSameMember(sender, receiver);
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.modifier = modifier;
        this.message = message;
        this.backgroundColor = backgroundColor;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
    }

    private void validateSameMember(Member sender, Member receiver) {
        if (sender.equals(receiver)) {
            throw new SameSenderReceiverException();
        }
    }
}
