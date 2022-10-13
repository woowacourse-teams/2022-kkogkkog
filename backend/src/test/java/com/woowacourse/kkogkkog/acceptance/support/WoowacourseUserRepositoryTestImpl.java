package com.woowacourse.kkogkkog.acceptance.support;

import com.woowacourse.kkogkkog.infrastructure.domain.WoowacourseUserRepository;
import org.springframework.stereotype.Component;

@Component
public class WoowacourseUserRepositoryTestImpl implements WoowacourseUserRepository {

    @Override
    public boolean contains(String email) {
        return false;
    }

    @Override
    public String getUserId(String email) {
        return "userId";
    }
}
