package com.woowacourse.kkogkkog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false)
    private String modifier;

    @Column(nullable = false)
    private String backgroundColor;

    @Column(nullable = false)
    private CouponType couponType;

    public CouponTemplate(Long id, Long memberId, String modifier, String backgroundColor, CouponType couponType) {
        this.id = id;
        this.memberId = memberId;
        this.modifier = modifier;
        this.backgroundColor = backgroundColor;
        this.couponType = couponType;
    }
}
