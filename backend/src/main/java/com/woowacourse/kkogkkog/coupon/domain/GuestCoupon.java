package com.woowacourse.kkogkkog.coupon.domain;

import com.woowacourse.kkogkkog.common.domain.BaseEntity;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.util.UUID;
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
public class GuestCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String couponCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_member_id", nullable = false)
    private Member sender;

    @Column(name = "hashtag", nullable = false)
    private String couponTag;

    @Column(name = "description", nullable = false)
    private String couponMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType couponType;

    public GuestCoupon(Long id, String couponCode, Member sender, String couponTag,
                       String couponMessage, CouponType couponType) {
        this.id = id;
        this.couponCode = couponCode;
        this.sender = sender;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
    }

    public GuestCoupon(String couponCode, Member sender, String couponTag,
                       String couponMessage, CouponType couponType) {
        this(null, couponCode, sender, couponTag, couponMessage, couponType);
    }

    public static GuestCoupon of(Member sender, String couponTag,
                                 String couponMessage, CouponType couponType) {
        UUID uuid = UUID.randomUUID();
        return new GuestCoupon(uuid.toString(), sender, couponTag, couponMessage, couponType);
    }
}
