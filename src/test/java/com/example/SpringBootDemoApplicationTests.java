package com.example;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mapper.UserMapper;
import com.example.model.User;
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
import java.util.List;

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
    private UserMapper userMapper;
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

    @Test
    public void testSelect() {
        // User user0 = new User();
        // user0.setName("李四");
        // int i = userMapper.insert(user0);
        // log.info("insert::{}", i);

        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            log.info("userList::{}::{}", user.getId(), user.getName());
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "李四");
        Page<User> page = new Page<>(2, 5);

        IPage<User> userIPage = userMapper.selectPage(page, wrapper);
        List<User> records = userIPage.getRecords();
        log.info("records::{}::{}", records, userIPage);
        log.info("userIPage::{}::{}::{}::{}", userIPage.getCurrent(), userIPage.getSize(), userIPage.getPages(), userIPage.getTotal());

        // User user = userMapper.selectByName("张三");
        // log.info("user::{}::{}", user.getId(), user.getName());

        User user1 = userMapper.selectById("1");
        log.info("user1::{}::{}", user1.getId(), user1.getName());
    }
}
