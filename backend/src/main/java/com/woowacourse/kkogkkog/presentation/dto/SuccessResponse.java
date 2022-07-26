package com.woowacourse.kkogkkog.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SuccessResponse<T> {

    private T data;

    public SuccessResponse(T data) {
        this.data = data;
    }
}
