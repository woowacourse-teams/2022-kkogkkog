package com.woowacourse.kkogkkog.common.replication;

import java.util.List;

public class RoutingCircular<T> {

    private List<T> dataSources;
    private Integer counter = 0;

    public RoutingCircular(final List<T> dataSources) {
        this.dataSources = dataSources;
    }

    // Test 필요하다
    public T getOne() {
        int circularSize = dataSources.size();
        if (counter + 1 > circularSize) {
            counter = 0;
        }
        return dataSources.get(counter++ % circularSize);
    }
}
