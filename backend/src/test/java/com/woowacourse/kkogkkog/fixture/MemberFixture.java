package com.woowacourse.kkogkkog.fixture;

import com.woowacourse.kkogkkog.domain.Member;

public class MemberFixture {

    public static Member ROOKIE = new Member(null, "URookie", "T03LX3C5540",
        "루키", "image");
    public static Member ARTHUR = new Member(null, "UArthur", "T03LX3C5540",
        "아서", "image");
    public static Member JEONG = new Member(null, "UJeong", "T03LX3C5540", "정", "image");
    public static Member LEO = new Member(null, "ULeo", "T03LX3C5540", "레오", "image");
    public static Member NON_EXISTING_MEMBER = new Member(99999L, "UNonExistingMember", "T03LX3C5540",
        "존재하지_않는_사용자", "image");
}
