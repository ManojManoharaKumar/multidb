package com.sample.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceRouter extends AbstractRoutingDataSource{

    @Override
    protected Object determineCurrentLookupKey() {
        return DIYDatabaseContextHolder.getDiyDatabase();
    }
}
