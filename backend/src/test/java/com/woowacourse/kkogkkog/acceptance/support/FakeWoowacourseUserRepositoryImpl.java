package com.woowacourse.kkogkkog.acceptance.support;

import com.woowacourse.kkogkkog.infrastructure.domain.WoowacourseUserRepository;
import java.util.Optional;

public class FakeWoowacourseUserRepositoryImpl implements WoowacourseUserRepository {

    @Override
    public boolean contains(String email) {
        return false;
    }

    @Override
    public Optional<String> getUserId(String email) {
        return Optional.of("userId");
    }
}
