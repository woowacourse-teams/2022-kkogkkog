package com.woowacourse.kkogkkog.coupon.domain;

import com.woowacourse.kkogkkog.coupon.domain.event.CouponEvent;
import com.woowacourse.kkogkkog.coupon.domain.event.CouponEventType;
import com.woowacourse.kkogkkog.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "member_history")
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_member_id")
    private Member hostMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_member_id")
    private Member targetMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_event")
    private CouponEventType couponEventType;

    private LocalDateTime meetingDate;

    private String message;

    private Boolean isRead = false;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdTime;

    public CouponHistory(Long id, Member hostMember, Member targetMember, Coupon coupon,
                         CouponEventType couponEventType, LocalDateTime meetingDate,
                         String message) {
        this.id = id;
        this.hostMember = hostMember;
        this.targetMember = targetMember;
        this.coupon = coupon;
        this.couponEventType = couponEventType;
        this.meetingDate = meetingDate;
        this.message = message;
    }

    public CouponHistory(Member hostMember, Member targetMember, Coupon coupon,
                         CouponEvent couponEvent, String message) {
        this(null, hostMember, targetMember, coupon, couponEvent.getType(),
            couponEvent.getMeetingDate(), message);
    }

    public static CouponHistory ofNew(Coupon coupon) {
        Member historyHostMember = coupon.getReceiver();
        CouponEvent initEvent = new CouponEvent(CouponEventType.INIT, null);
        return new CouponHistory(historyHostMember, coupon.getSender(), coupon, initEvent, null);
    }

    public static CouponHistory ofNewByCouponCode(Coupon coupon) {
        Member historyHostMember = coupon.getSender();
        CouponEvent initEvent = new CouponEvent(CouponEventType.RECEIVE, null);
        return new CouponHistory(historyHostMember, coupon.getReceiver(), coupon, initEvent, null);
    }

    public static CouponHistory of(Member loginMember, Coupon coupon, CouponEvent event, String message) {
        Member historyHostMember = coupon.getOppositeMember(loginMember);
        return new CouponHistory(historyHostMember, loginMember, coupon, event, message);
    }

    public void updateIsRead() {
        isRead = true;
    }

    public String toNoticeMessage() {
        return couponEventType.generateNoticeMessage(targetMember, coupon.getCouponType());
    }
}
