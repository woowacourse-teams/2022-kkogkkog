package com.woowacourse.kkogkkog.coupon.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "member_history")
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponHistory2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "host_member_id")
    private Long hostMemberId;

    @Column(name = "target_member_id")
    private Long targetMemberId;

    @Column(name = "coupon_id")
    private Long couponId;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_event")
    private CouponEventType couponEventType;

    private LocalDateTime meetingDate;

    private String message;

    private Boolean isRead;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdTime;

    public CouponHistory2(Long hostMemberId, Long targetMemberId, Long couponId,
                          CouponEventType couponEventType, LocalDateTime meetingDate,
                          String message, boolean isRead) {
        this.id = null;
        this.hostMemberId = hostMemberId;
        this.targetMemberId = targetMemberId;
        this.couponId = couponId;
        this.couponEventType = couponEventType;
        this.meetingDate = meetingDate;
        this.message = message;
        this.isRead = isRead;
    }

    public String getCouponEventType() {
        return couponEventType.name();
    }

    public LocalDateTime getCreatedTime() {
        return LocalDateTime.now();
    }
}
