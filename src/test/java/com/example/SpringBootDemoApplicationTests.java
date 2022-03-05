package com.example;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.example.system.controller.BaseController;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;

@Slf4j
@SpringBootTest
@MapperScan("com.example.mapper")
class SpringBootDemoApplicationTests {

    //数据源组件
    @Resource
    DataSource dataSource;

    @Resource
    ApplicationContext context;

    @Resource
    private StringEncryptor stringEncryptor;


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
    }


    @Test
    void encrypt() {
        log.info("stringEncryptor::{}", stringEncryptor.getClass().getTypeName());
        log.info("root::{}", stringEncryptor.encrypt("root"));
    }

    @Test
    @SneakyThrows
    void mybatisGenerator() {
        String basePath = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX).getParentFile().getParentFile().getPath();
        String outputDir = basePath + "/mybatis";
        String outputXmlDir = outputDir + "/mapper";
        log.info("outputDir:{}. outputXmlDir:{}.", outputDir, outputXmlDir);

        String author = "zhangxv";
        String parent = "com.example";
        String[] tables = {"t_security_config", "t_business_log", "t_flow_log"};

        FastAutoGenerator.create(new DataSourceConfig.Builder(dataSource)).globalConfig(builder -> {
            builder.author(author)
                    // .enableSwagger()
                    .fileOverride()
                    .disableOpenDir()
                    .outputDir(outputDir);

        }).packageConfig(builder -> {
            builder.parent(parent)
                    .entity("model")
                    .service("service")
                    .serviceImpl("service.impl")
                    .mapper("mapper")
                    .xml("mapper.xml")
                    .controller("controller")
                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml, outputXmlDir));
        }).strategyConfig(builder -> {
            builder.addInclude(tables)
                    .addTablePrefix("t_")

                    //Entity配置
                    .entityBuilder()
                    .disableSerialVersionUID()
                    .enableTableFieldAnnotation()

                    //Controller配置
                    .controllerBuilder()
                    .superClass(BaseController.class)
                    .enableRestStyle()
                    .formatFileName("%sController")

                    //Service配置
                    .serviceBuilder()
                    // .superServiceClass(BaseService.class)
                    // .superServiceImplClass(BaseServiceImpl.class)
                    .formatServiceFileName("%sService")
                    .formatServiceImplFileName("%sServiceImp")

                    //Mapper配置
                    .mapperBuilder()
                    .superClass(BaseMapper.class)
                    .enableMapperAnnotation()
                    .enableBaseResultMap()
                    // .cache(MyMapperCache.class)
                    .formatMapperFileName("%sMapper")
                    .build();
        }).templateEngine(new FreemarkerTemplateEngine()).execute();
    }
}
