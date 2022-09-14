package com.woowacourse.kkogkkog.auth.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberUpdateResponse {

    private Long id;
    private Boolean isNew;

    public MemberUpdateResponse(Long id, Boolean isNew) {
        this.id = id;
        this.isNew = isNew;
    }
}
