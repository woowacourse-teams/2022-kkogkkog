package com.woowacourse.kkogkkog.fixture;

import static com.woowacourse.kkogkkog.fixture.WorkspaceFixture.*;

import com.woowacourse.kkogkkog.domain.Member;

public class MemberFixture {

    public static Member ROOKIE = new Member(null, "URookie", WORKSPACE,
        "루키", "rookie@gmail.com", "image");
    public static Member ARTHUR = new Member(null, "UArthur", WORKSPACE,
        "아서", "arthur@gmail.com", "image");
    public static Member JEONG = new Member(null, "UJeong", WORKSPACE,
        "정", "jeong@gmail.com", "image");
    public static Member LEO = new Member(null, "ULeo", WORKSPACE,
        "레오", "leothelion@gmail.com", "image");
    public static Member NON_EXISTING_MEMBER = new Member(99999L, "UNonExistingMember",
        WORKSPACE, "존재하지_않는_사용자", "abc@gmail.com", "image");
}
