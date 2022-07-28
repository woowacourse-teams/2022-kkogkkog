package com.woowacourse.kkogkkog.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberOAuthResponse {

    private Long id;
    private Boolean isCreated;

    public MemberOAuthResponse(Long id, Boolean isCreated) {
        this.id = id;
        this.isCreated = isCreated;
    }
}
