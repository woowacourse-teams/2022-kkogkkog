package com.woowacourse.kkogkkog.acceptance.support;

import com.woowacourse.kkogkkog.infrastructure.domain.WoowacourseUserRepository;

public class FakeWoowacourseUserRepositoryImpl implements WoowacourseUserRepository {

    @Override
    public boolean contains(String email) {
        return false;
    }

    @Override
    public String getUserId(String email) {
        return "userId";
    }
}
