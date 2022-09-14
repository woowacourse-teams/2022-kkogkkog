package com.woowacourse.kkogkkog.member.domain;

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
public class WorkspaceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_member_id", nullable = false)
    private Member masterMember;

    @Column(nullable = false, unique = true)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String imageUrl;

    public WorkspaceUser(Member masterMember, String userId, Workspace workspace,
                         String displayName,
                         String email, String imageUrl) {
        this(null, masterMember, userId, workspace, displayName, email, imageUrl);
    }

    public WorkspaceUser(Long id, Member masterMember, String userId, Workspace workspace,
                         String displayName, String email, String imageUrl) {
        this.id = id;
        this.masterMember = masterMember;
        this.userId = userId;
        this.workspace = workspace;
        this.displayName = displayName;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public void updateDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void updateImageURL(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
