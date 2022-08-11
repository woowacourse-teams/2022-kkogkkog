package com.woowacourse.kkogkkog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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

    @OneToOne(fetch = FetchType.LAZY)
    private Member masterMember;

    @Column(nullable = false, unique = true)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String imageUrl;

    public WorkspaceMember(Long id, Member masterMember, String userId, Workspace workspace,
                           String email, String imageUrl) {
        this.id = id;
        this.masterMember = masterMember;
        this.userId = userId;
        this.workspace = workspace;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static WorkspaceMember of(Member member) {
        return new WorkspaceMember(null, member, member.getUserId(), member.getWorkspace(),
            member.getEmail(), member.getImageUrl());
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateImageURL(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
