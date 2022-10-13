package com.woowacourse.kkogkkog.infrastructure.domain;

import com.woowacourse.kkogkkog.infrastructure.application.WoowacoursePushAlarmClient;
import com.woowacourse.kkogkkog.infrastructure.dto.WoowacourseUserResponse;
import com.woowacourse.kkogkkog.infrastructure.dto.WoowacourseUsersResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class WoowacourseUserRepositoryImpl implements WoowacourseUserRepository {

    private Map<String, String> cache = new ConcurrentHashMap<>();

    private final WoowacoursePushAlarmClient woowacoursePushAlarmClient;

    public WoowacourseUserRepositoryImpl(WoowacoursePushAlarmClient woowacoursePushAlarmClient) {
        this.woowacoursePushAlarmClient = woowacoursePushAlarmClient;
    }

    private void refreshCache() {
        Map<String, String> responseMap = new ConcurrentHashMap<>();
        WoowacourseUsersResponse response = woowacoursePushAlarmClient.requestUsers();
        for (WoowacourseUserResponse member : response.getMembers()) {
            String email = member.getProfile().getEmail();
            String userId = member.getId();
            responseMap.put(email, userId);
        }
        cache = responseMap;
    }

    @Override
    public boolean contains(String email) {
        checkCache();
        return cache.containsKey(email);
    }

    @Override
    public String getUserId(String email) {
        checkCache();
        return cache.get(email);
    }

    private void checkCache() {
        if (cache.isEmpty()) {
            refreshCache();
        }
    }
}
