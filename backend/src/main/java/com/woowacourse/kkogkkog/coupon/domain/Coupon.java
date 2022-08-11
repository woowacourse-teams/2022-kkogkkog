package com.woowacourse.kkogkkog.coupon.domain;

import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.coupon.exception.SameSenderReceiverException;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_member_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_member_id")
    private Member receiver;

    private String description;

    private String hashtag;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType couponType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus couponStatus;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

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
