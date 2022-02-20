package com.example.system.config.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.spi.KeyGenerateAlgorithm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DefaultKeyGenerateAlgorithm implements KeyGenerateAlgorithm {
    @Override
    public Comparable<?> generateKey() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("0000MMddHHmmss"));
    }

    @Override
    public void init() {
        log.debug("DefaultKeyGenerateAlgorithm init");
    }

    @Override
    public String getType() {
        return "default-key-generate-algorithm";
    }
}
