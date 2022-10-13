package com.woowacourse.kkogkkog.infrastructure.domain;

public interface WoowacourseUserRepository {

    boolean contains(String email);

    String getUserId(String email);

}
