package com.woowacourse.kkogkkog.infrastructure;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCreateResponse {

    private Long id;
    private Boolean isNew;

    public MemberCreateResponse(Long id, Boolean isNew) {
        this.id = id;
        this.isNew = isNew;
    }
}
