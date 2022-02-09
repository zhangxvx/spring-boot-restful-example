package com.example.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

@Slf4j
public final class PreciseModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(final Collection<String> tableNames, final PreciseShardingValue<String> shardingValue) {
        for (String each : tableNames) {
            String value = shardingValue.getValue();
            String substring = value.substring(16);
            log.debug("table:{}. value:{}->{}", each, value, substring);
            if (each.endsWith(Integer.toString(Integer.parseInt(substring) % 2))) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}