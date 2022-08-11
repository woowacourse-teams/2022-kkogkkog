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
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workspaceId;

    private String name;

    private String accessToken;

    public Workspace(Long id, String workspaceId, String name, String accessToken) {
        this.id = id;
        this.name = name;
        this.workspaceId = workspaceId;
        this.accessToken = accessToken;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
