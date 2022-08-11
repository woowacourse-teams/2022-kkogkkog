package com.woowacourse.kkogkkog.domain;

import javax.persistence.Column;
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
public class WorkspaceMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String imageUrl;

    public WorkspaceMember(Long id, String userId, Workspace workspace, String nickname,
                           String email,
                           String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.workspace = workspace;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateImageURL(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
