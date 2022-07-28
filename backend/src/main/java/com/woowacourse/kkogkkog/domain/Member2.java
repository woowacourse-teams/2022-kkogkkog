package com.woowacourse.kkogkkog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String imageUri;

    @Column(nullable = false, unique = true)
    private String socialId;

    @Column(nullable = false)
    private String teamId;

    public Member2(Long id, String nickname, String imageUri, String socialId, String teamId) {
        this.id = id;
        this.nickname = nickname;
        this.imageUri = imageUri;
        this.socialId = socialId;
        this.teamId = teamId;
    }

    public void updateImage(String imageUri) {
        this.imageUri = imageUri;
    }
}
