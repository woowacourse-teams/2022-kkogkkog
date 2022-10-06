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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE unregistered_coupon c SET c.deleted = true WHERE c.id=?")
@Getter
public class UnregisteredCoupon extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnregisteredCouponStatus unregisteredCouponStatus;

    @Column(nullable = false)
    private boolean deleted;

    public UnregisteredCoupon(Long id,
                              String couponCode,
                              Member sender,
                              String couponTag,
                              String couponMessage,
                              CouponType couponType,
                              UnregisteredCouponStatus unregisteredCouponStatus) {
        this.id = id;
        this.couponCode = couponCode;
        this.sender = sender;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
        this.unregisteredCouponStatus = unregisteredCouponStatus;
    }

    public UnregisteredCoupon(String couponCode,
                              Member sender,
                              String couponTag,
                              String couponMessage,
                              CouponType couponType,
                              UnregisteredCouponStatus unregisteredCouponStatus) {
        this(null, couponCode, sender, couponTag, couponMessage, couponType,
            unregisteredCouponStatus);
    }

    public static UnregisteredCoupon of(Member sender, String couponTag, String couponMessage,
                                        CouponType couponType) {
        UUID uuid = UUID.randomUUID();
        return new UnregisteredCoupon(uuid.toString(), sender, couponTag, couponMessage, couponType,
            UnregisteredCouponStatus.ISSUED);
    }

    public Coupon toCoupon(Member receiver) {
        return new Coupon(sender, receiver, couponTag, couponMessage, couponType);
    }

    public boolean isNotSender(Member member) {
        return sender != member;
    }
}
