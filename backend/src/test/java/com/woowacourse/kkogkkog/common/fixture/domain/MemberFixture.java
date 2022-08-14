package com.woowacourse.kkogkkog.common.fixture.domain;

import static com.woowacourse.kkogkkog.fixture.WorkspaceFixture.KKOGKKOG;

import com.woowacourse.kkogkkog.domain.Member;
import com.woowacourse.kkogkkog.domain.Nickname;
import com.woowacourse.kkogkkog.domain.Workspace;

public enum MemberFixture {

    ROOKIE("rookieId1", "루키", "rookie@gmail.com", "https://slack"),
    AUTHOR("authorId2", "아서", "author@gmail.com", "https://slack"),
    JEONG("jeongId3", "진우", "jeong@gmail.com", "https://slack"),
    LEO("leoId4", "레오", "leo@gmail.com", "https://slack"),

    SENDER("senderId1", "루키", "sender@gmail.com", "https://slack"),
    RECEIVER("receiverId1", "아서", "receiver@gmail.com", "https://slack"),
    RECEIVER2("receiverId2", "진우", "receiver@gmail.com", "https://slack"),
    ;

    private final String userId;
    private final String nickname;
    private final String email;
    private final String imageUrl;

    MemberFixture(String userId,
                  String nickname,
                  String email,
                  String imageUrl) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public Member getMember(Workspace workspace) {
        return new Member(null, userId, workspace, new Nickname(nickname), email, imageUrl);
    }

    public Member getMember() {
        return new Member(null, userId, KKOGKKOG.getWorkspace(1L), new Nickname(nickname), email, imageUrl);
    }

    public Member getMember(Long id) {
        return new Member(id, userId, null, new Nickname(nickname), email, imageUrl);
    }

    public Member getMember(Long id, Workspace workspace) {
        return new Member(id, userId, workspace, new Nickname(nickname), email, imageUrl);
    }
}
