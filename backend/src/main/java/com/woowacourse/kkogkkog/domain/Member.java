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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String workspaceId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String imageUrl;

    public Member(Long id, String userId, String workspaceId, String nickname, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public void updateImageURL(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
