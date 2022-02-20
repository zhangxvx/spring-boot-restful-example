package com.example;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

@Slf4j
@EnableCaching
@MapperScan("com.example.business.mapper")
@ServletComponentScan
@SpringBootApplication
public class SpringBootDemoApplication implements CommandLineRunner {
    @Resource
    ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            log.debug("{}:{}", name, applicationContext.getBean(name).getClass());
        }
    }
}
