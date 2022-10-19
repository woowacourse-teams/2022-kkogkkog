package com.woowacourse.kkogkkog.common.replication;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    private RoutingCircular<String> routingCircular;

    // slave List를 주입받아서 리스트로 넣어준다.
    @Override
    public void setTargetDataSources(final Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);

        routingCircular = new RoutingCircular<>(
            targetDataSources.keySet().stream()
                .map(Object::toString)
                .filter(key -> key.contains("slave"))
                .collect(Collectors.toList())
        );
    }

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        System.out.println("Transaction의 Read Only가 " + isReadOnly + " 입니다.");
        if (isReadOnly) {
            return routingCircular.getOne();
        }
        return "master";
    }
}
