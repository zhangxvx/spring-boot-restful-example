package com.example.config.datasource;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;

public final class RangeModuloShardingTableAlgorithm implements RangeShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(final Collection<String> tableNames, final RangeShardingValue<String> shardingValue) {
        return new LinkedHashSet<>(tableNames);
    }
}