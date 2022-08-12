package com.woowacourse.kkogkkog.common.fixture.domain;

import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.Workspace;
import com.woowacourse.kkogkkog.fixture.WorkspaceFixture;

public enum MemberFixture {

    ROOKIE("rookieId1", "workspaceId", "루키", "rookie@gmail.com", "https://slack"),
    AUTHOR("authorId2", "workspaceId", "아서", "author@gmail.com", "https://slack"),
    JEONG("jeongId3", "workspaceId", "정", "jeong@gmail.com", "https://slack"),
    LEO("leoId4", "workspaceId", "레오", "leo@gmail.com", "https://slack"),

    SENDER("senderId1", "workspaceId", "루키", "sender@gmail.com", "https://slack"),
    RECEIVER("receiverId1", "workspaceId", "아서", "receiver@gmail.com", "https://slack"),
    RECEIVER2("receiverId2", "workspaceId", "정", "receiver@gmail.com", "https://slack"),
    ;

    private final String userId;
    private final String workspaceId;
    private final String nickname;
    private final String email;
    private final String imageUrl;

    MemberFixture(String userId,
                  String workspaceId,
                  String nickname,
                  String email,
                  String imageUrl) {
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public Member getMember(Workspace workspace) {
        return new Member(null, userId, workspace, nickname, email, imageUrl);
    }

    public Member getMember() {
        return new Member(null, userId, WorkspaceFixture.WORKSPACE, nickname, email, imageUrl);
    }

    public Member getMember(Long id) {
        return new Member(id, userId, null, nickname, email, imageUrl);
    }
}
