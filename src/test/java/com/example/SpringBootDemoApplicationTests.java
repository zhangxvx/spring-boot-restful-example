package com.example;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@SpringBootTest
@MapperScan("com.example.mapper")
class SpringBootDemoApplicationTests {

    //数据源组件
    @Autowired
    DataSource dataSource;
    //用于访问数据库的组件
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ApplicationContext context;

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    void contextLoads() {

    }

    @Test
    void printBeans() {
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            log.info(beanDefinitionName);
        }
    }

    @Test
    void jdbcTest() throws SQLException {
        log.info("默认数据源为：{}", dataSource.getClass());
        log.info("数据库连接实例：{}", dataSource.getConnection());
        //访问数据库
        Integer i = jdbcTemplate.queryForObject("SELECT count(*) from `user`", Integer.class);
        log.info("user 表中共有{}条数据。", i);
    }


    @Test
    void encrypt() {
        log.info("stringEncryptor::{}", stringEncryptor.getClass().getTypeName());
        log.info("root::{}", stringEncryptor.encrypt("root"));
    }
}
