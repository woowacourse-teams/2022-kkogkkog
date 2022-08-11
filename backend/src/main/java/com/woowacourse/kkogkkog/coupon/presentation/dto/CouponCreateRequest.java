package com.woowacourse.kkogkkog.coupon.presentation.dto;

import com.woowacourse.kkogkkog.coupon.application.dto.CouponSaveRequest;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponCreateRequest {

    @NotEmpty(message = "받는 사람의 아이디를 입력해주세요")
    private List<Long> receiverIds;

    private String hashtag;

    private String description;

    @NotBlank(message = "쿠폰 타입을 입력해주세요")
    private String couponType;

    public CouponCreateRequest(List<Long> receiverIds,
                               String hashTag,
                               String description,
                               String couponType) {
        this.receiverIds = receiverIds;
        this.hashtag = hashTag;
        this.description = description;
        this.couponType = couponType;
    }

    public CouponSaveRequest toCouponSaveRequest(Long senderId) {
        return new CouponSaveRequest(
            senderId,
            receiverIds,
            hashtag,
            description,
            couponType);
    }
}
