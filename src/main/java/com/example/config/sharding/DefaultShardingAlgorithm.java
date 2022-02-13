package com.example.config.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashSet;

@Slf4j
public class DefaultShardingAlgorithm implements StandardShardingAlgorithm<String> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        for (String each : collection) {
            String value = preciseShardingValue.getValue();
            String substring = value.substring(value.length() - 1);
            log.debug("table:{}. value:{}->{}", each, value, substring);
            if (each.endsWith(Integer.toString(Integer.parseInt(substring) % 2))) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<String> rangeShardingValue) {
        return new LinkedHashSet<>(collection);
    }

    @Override
    public void init() {
        log.debug("DefaultShardingAlgorithm init");
    }

    @Override
    public String getType() {
        return "default-sharding-algorithm";
    }
}
