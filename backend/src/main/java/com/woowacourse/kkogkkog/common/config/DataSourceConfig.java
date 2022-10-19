package com.woowacourse.kkogkkog.common.config;

import com.woowacourse.kkogkkog.common.replication.RoutingDataSource;
import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

//@Profile({"dev", "default"})
//@Configuration
public class DataSourceConfig {

    private static final String MASTER_SERVER = "MASTER";
    private static final String SLAVE1_SERVER = "SLAVE1";

    @Bean
    @Qualifier(MASTER_SERVER)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
            .build();
    }

    @Bean
    @Qualifier(SLAVE1_SERVER)
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create()
            .build();
    }

    @Bean
    public DataSource routingDataSource(@Qualifier(MASTER_SERVER) DataSource masterDataSource,
                                        @Qualifier(SLAVE1_SERVER) DataSource slave1DataSource) {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        HashMap<Object, Object> dataSources = new HashMap<>();
        dataSources.put("master", masterDataSource);
        dataSources.put("slave1", slave1DataSource);

        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSource determinedDataSource = routingDataSource(masterDataSource(), slave1DataSource());
        return new LazyConnectionDataSourceProxy(determinedDataSource);
    }
}

