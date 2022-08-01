package com.woowacourse.kkogkkog.presentation.dto;

import com.woowacourse.kkogkkog.application.dto.CouponChangeStatusRequest;
import com.woowacourse.kkogkkog.domain.CouponEvent;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponRequestEventRequest {

    private String couponEvent;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;

    public CouponRequestEventRequest(String couponEvent, LocalDate meetingDate) {
        this.couponEvent = couponEvent;
        this.meetingDate = meetingDate;
    }

    public CouponChangeStatusRequest toCouponChangeStatusRequest(Long loginMemberId,
                                                                 Long couponId) {
        return new CouponChangeStatusRequest(loginMemberId, couponId, CouponEvent.of(couponEvent), meetingDate);
    }
}
