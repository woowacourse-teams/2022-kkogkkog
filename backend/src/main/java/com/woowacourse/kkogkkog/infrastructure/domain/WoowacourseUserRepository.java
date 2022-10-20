package com.woowacourse.kkogkkog.infrastructure.domain;

import java.util.Optional;

public interface WoowacourseUserRepository {

    boolean contains(String email);

    Optional<String> getUserId(String email);

}
