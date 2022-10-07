package com.woowacourse.kkogkkog.infrastructure.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserDto {

    private String name;
    private String email;
    private String picture;

    public GoogleUserDto(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
