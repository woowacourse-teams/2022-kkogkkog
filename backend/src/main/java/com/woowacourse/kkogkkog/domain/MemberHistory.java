package com.woowacourse.kkogkkog.domain;

import javax.persistence.Entity;
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
public class MemberHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_member_id")
    private Member hostMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_member_id")
    private Member targetMember;

    private CouponType couponType;

    private CouponEvent couponEvent;

    public MemberHistory(Long id, Member hostMember, Member targetMember, CouponType couponType,
                         CouponEvent couponEvent) {
        this.id = id;
        this.hostMember = hostMember;
        this.targetMember = targetMember;
        this.couponType = couponType;
        this.couponEvent = couponEvent;
    }
}
