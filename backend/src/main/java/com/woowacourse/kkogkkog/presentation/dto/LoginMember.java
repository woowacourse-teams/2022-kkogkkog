package com.woowacourse.kkogkkog.presentation.dto;

import lombok.Getter;

@Getter
public class LoginMember {

    private Long id;

    public LoginMember(Long id) {
        this.id = id;
    }
}
