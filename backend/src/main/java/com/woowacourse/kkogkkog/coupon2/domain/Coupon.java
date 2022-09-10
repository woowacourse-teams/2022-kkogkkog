package com.woowacourse.kkogkkog.coupon2.domain;

import com.woowacourse.kkogkkog.common.domain.BaseEntity;
import com.woowacourse.kkogkkog.coupon2.exception.SameSenderReceiverException;
import com.woowacourse.kkogkkog.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_member_id", nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_member_id", nullable = false)
    private Member receiver;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String hashtag;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType couponType;

    @Embedded
    private CouponState couponState;

    public Coupon(Long id, Member sender, Member receiver, String hashtag, String description,
                  CouponType couponType, CouponState couponState) {
        validateSameMember(sender, receiver);
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.hashtag = hashtag;
        this.description = description;
        this.couponType = couponType;
        this.couponState = couponState;
    }

    public Coupon(Member sender, Member receiver, String hashtag, String description,
                  CouponType couponType) {
        this(null, sender, receiver, hashtag, description, couponType, CouponState.ofReady());
    }

    private void validateSameMember(Member sender, Member receiver) {
        if (sender.equals(receiver)) {
            throw new SameSenderReceiverException();
        }
    }

    public Member getOppositeMember(Member member) {
        if (sender == member) {
            return receiver;
        }
        return sender;
    }

    public void changeState(CouponEvent couponEvent, Member member) {
        couponEvent.checkExecutable(sender.equals(member), receiver.equals(member));
        couponState.changeStatus(couponEvent);
    }
}