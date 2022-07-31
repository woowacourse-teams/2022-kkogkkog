package com.woowacourse.kkogkkog.infrastructure;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCreateResponse {

    private Long id;
    private Boolean isCreated;

    public MemberCreateResponse(Long id, Boolean isCreated) {
        this.id = id;
        this.isCreated = isCreated;
    }
}
