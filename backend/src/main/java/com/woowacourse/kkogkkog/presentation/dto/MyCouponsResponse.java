package com.woowacourse.kkogkkog.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyCouponsResponse {

    private CouponsResponse data;

    public MyCouponsResponse(CouponsResponse data) {
        this.data = data;
    }
}
