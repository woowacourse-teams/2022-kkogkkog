//package com.woowacourse.kkogkkog.member.domain;
//
//import com.woowacourse.kkogkkog.coupon2.domain.CouponEvent;
//import com.woowacourse.kkogkkog.coupon2.domain.CouponType;
//import java.time.LocalDateTime;
//import javax.persistence.Column;
//import javax.persistence.Embedded;
//import javax.persistence.Entity;
//import javax.persistence.EntityListeners;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//@EntityListeners(AuditingEntityListener.class)
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//public class MemberHistory {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "host_member_id")
//    private Member hostMember;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "target_member_id")
//    private Member targetMember;
//
//    @Column(name = "coupon_id") // TODO: 버그 Fix 목적으로 임시 추가
//    private Long couponId;
//
//    @Enumerated(EnumType.STRING)
//    private CouponType couponType;
//
//    @Embedded
//    private CouponEvent couponEvent;
//
//    private String message;
//
//    private Boolean isRead = false;
//
//    @CreatedDate
//    private LocalDateTime createdTime;
//
//    public MemberHistory(Long id, Member hostMember, Member targetMember, Long couponId,
//                         CouponType couponType, String message) {
//        this.id = id;
//        this.hostMember = hostMember;
//        this.targetMember = targetMember;
//        this.couponId = couponId;
//        this.couponType = couponType;
////        this.couponEvent = null;
//        this.message = message;
//    }
//
//    public void updateIsRead() {
//        isRead = true;
//    }
//
////    public String toNoticeMessage() {
////        return couponEvent.getType().generateNoticeMessage(targetMember, couponType);
////    }
//}
