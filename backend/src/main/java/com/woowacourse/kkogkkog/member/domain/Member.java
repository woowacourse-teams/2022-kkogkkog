package com.woowacourse.kkogkkog.member.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private String email;

    @Column(nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Embedded
    private Nickname nickname;

    @Column(nullable = false)
    private String imageUrl;

    public Member(Long id, String userId, Workspace workspace, Nickname nickname, String email,
                  String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.workspace = workspace;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public String getNickname() {
        return nickname.getValue();
    }

    public void updateMainSlackUserId(String userId) {
        this.userId = userId;
    }

    public void updateNickname(String nickname) {
        this.nickname = new Nickname(nickname);
    }

    public void updateImageURL(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
}
