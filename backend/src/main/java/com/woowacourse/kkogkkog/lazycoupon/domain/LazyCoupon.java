package com.woowacourse.kkogkkog.lazycoupon.domain;

import com.woowacourse.kkogkkog.common.domain.BaseEntity;
import com.woowacourse.kkogkkog.coupon.domain.Coupon;
import com.woowacourse.kkogkkog.coupon.domain.CouponType;
import com.woowacourse.kkogkkog.member.domain.Member;
import com.woowacourse.kkogkkog.lazycoupon.exception.LazyCouponQuantityExcessException;
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
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Table(name = "unregistered_coupon")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE unregistered_coupon c SET c.deleted = true WHERE c.id=?")
@Getter
public class LazyCoupon extends BaseEntity {

    private static final int MINIMUM_QUANTITY = 0;
    private static final int MAXIMUM_QUANTITY = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String couponCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_member_id", nullable = false)
    private Member sender;

    @Column(nullable = false)
    private String couponTag;

    @Column(nullable = false)
    private String couponMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType couponType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "unregistered_coupon_status")
    private LazyCouponStatus lazyCouponStatus;

    @Column(nullable = false)
    private boolean deleted;

    public LazyCoupon(Long id,
                      String couponCode,
                      Member sender,
                      String couponTag,
                      String couponMessage,
                      CouponType couponType,
                      LazyCouponStatus lazyCouponStatus) {
        this.id = id;
        this.couponCode = couponCode;
        this.sender = sender;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
        this.lazyCouponStatus = lazyCouponStatus;
    }

    public LazyCoupon(String couponCode,
                      Member sender,
                      String couponTag,
                      String couponMessage,
                      CouponType couponType,
                      LazyCouponStatus lazyCouponStatus) {
        this(null, couponCode, sender, couponTag, couponMessage, couponType, lazyCouponStatus);
    }

    public static LazyCoupon of(Member sender, String couponTag, String couponMessage,
                                CouponType couponType) {
        UUID uuid = UUID.randomUUID();
        return new LazyCoupon(uuid.toString(), sender, couponTag, couponMessage, couponType, LazyCouponStatus.ISSUED);
    }

    public static void validateQuantity(int quantity) {
        if (quantity <= MINIMUM_QUANTITY || quantity > MAXIMUM_QUANTITY) {
            throw new LazyCouponQuantityExcessException();
        }
    }

    public Coupon toCoupon(Member receiver) {
        return new Coupon(sender, receiver, couponTag, couponMessage, couponType);
    }

    public void changeStatus(LazyCouponEventType lazyCouponEventType) {
        updateLazyCouponStatus(lazyCouponStatus.handle(lazyCouponEventType));
    }

    public boolean isNotSender(Member member) {
        return sender != member;
    }

    private void updateLazyCouponStatus(LazyCouponStatus lazyCouponStatus) {
        this.lazyCouponStatus = lazyCouponStatus;
    }
}
