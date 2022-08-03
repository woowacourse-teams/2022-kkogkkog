package com.woowacourse.kkogkkog.domain;

import com.woowacourse.kkogkkog.exception.InvalidRequestException;
import java.time.LocalDate;
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

    private Long couponId;

    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Enumerated(EnumType.STRING)
    private CouponEvent couponEvent;

    private LocalDate meetingDate;

    private Boolean isRead = false;

    public MemberHistory(Long id, Member hostMember,
                         Member targetMember, Long couponId,
                         CouponType couponType, CouponEvent couponEvent,
                         LocalDate meetingDate) {
        this.id = id;
        this.hostMember = hostMember;
        this.targetMember = targetMember;
        this.couponId = couponId;
        this.couponType = couponType;
        this.couponEvent = couponEvent;
        this.meetingDate = meetingDate;
    }

    public void updateIsRead() {
        isRead = true;
    }
}
