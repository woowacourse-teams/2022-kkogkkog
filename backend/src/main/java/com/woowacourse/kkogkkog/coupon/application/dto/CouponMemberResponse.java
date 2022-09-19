package com.woowacourse.kkogkkog.coupon.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Info : Coupon 과 관련된 Member 정보들만 추가시킬 것 [필드 추가 사용 금지] */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponMemberResponse {

    private Long id;
    private String nickname;
    private String imageUrl;

    public CouponMemberResponse(final Long id,
                                final String nickname,
                                final String imageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }
}
