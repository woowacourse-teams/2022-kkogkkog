package com.woowacourse.kkogkkog.coupon.domain;

import com.woowacourse.kkogkkog.common.domain.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "coupon")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon2 extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_member_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_member_id", nullable = false)
    private Long receiverId;

    @Column(name = "description", nullable = false)
    private String couponMessage;

    @Column(name = "hashtag", nullable = false)
    private String couponTag;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType couponType;

    @Embedded
    private CouponState couponState;

    public Coupon2(Long senderId,
                   Long receiverId,
                   String couponTag,
                   String couponMessage,
                   CouponType couponType,
                   CouponState couponState) {
        this.id = null;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.couponTag = couponTag;
        this.couponMessage = couponMessage;
        this.couponType = couponType;
        this.couponState = couponState;
    }

    public String getCouponType() {
        return couponType.name();
    }

    public String getCouponStatus() {
        return couponState.getCouponStatus().name();
    }

    public LocalDateTime getMeetingDate() {
        return couponState.getMeetingDate();
    }

    public LocalDateTime getCreatedTime() {
        return LocalDateTime.now();
    }

    public LocalDateTime getUpdatedTime() {
        return LocalDateTime.now();
    }
}
