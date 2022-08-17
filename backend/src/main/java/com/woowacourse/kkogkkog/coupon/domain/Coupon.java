package com.woowacourse.kkogkkog.coupon.domain;

import com.woowacourse.kkogkkog.coupon.exception.SameSenderReceiverException;
import com.woowacourse.kkogkkog.common.domain.BaseEntity;
import com.woowacourse.kkogkkog.member.domain.Member;
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
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_member_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_member_id")
    private Member receiver;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String hashtag;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType couponType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus couponStatus;

    public Coupon(Member sender, Member receiver, String hashtag, String description,
                  CouponType couponType, CouponStatus couponStatus) {
        this(null, sender, receiver, hashtag, description, couponType, couponStatus);
    }

    public Coupon(Long id, Member sender, Member receiver, String hashtag, String description,
                  CouponType couponType, CouponStatus couponStatus) {
        validateSameMember(sender, receiver);
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.hashtag = hashtag;
        this.description = description;
        this.couponType = couponType;
        this.couponStatus = couponStatus;
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

    public void changeStatus(CouponEvent couponEvent, Member member) {
        couponEvent.checkExecutable(sender.equals(member), receiver.equals(member));
        this.couponStatus = couponStatus.handle(couponEvent);
    }
}
