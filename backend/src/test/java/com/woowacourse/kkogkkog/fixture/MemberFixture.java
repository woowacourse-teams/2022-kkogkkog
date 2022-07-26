package com.woowacourse.kkogkkog.fixture;

import com.woowacourse.kkogkkog.domain.Member;

public class MemberFixture {

    public static Member ROOKIE = new Member(null, "rookie@gmail.com", "password1234!", "루키");
    public static Member ARTHUR = new Member(null, "arthur@gmail.com", "password1234!", "아서");
    public static Member JEONG = new Member(null, "jeong@gmail.com", "password1234!", "정");
    public static Member LEO = new Member(null, "leo@gmail.com", "password1234!", "레오");
    public static Member NON_EXISTING_MEMBER = new Member(99999L, "no-one@gmail.com",
        "password1234!", "존재하지_않는_사용자");
}
