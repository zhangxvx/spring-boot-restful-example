package com.example;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.example.controller.base.BaseController;
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.core.env.StandardEnvironment;

import java.util.Collections;

public class MybatisPlusCodeGenerator {

    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/db_test?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=GMT%2B8";
        System.setProperty("jasypt.encryptor.password", "123456");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
        String username = stringEncryptor.decrypt("fiwfxo+yRUr5KE2Pxi6rrbu/ud5xWzLKYYTbqtU+no0cPHxdjpn61uZC/5AKol8L");
        String password = stringEncryptor.decrypt("tfvDzl96dZ0jNyTxZ7nVpBZKlRD8YV+8QHp2RFMELNkict3cbar2z6W83nIKqLZw");
        String outputDir = "D:\\Workspaces\\Java\\spring-boot-restful-example\\mybatis";
        String outputXmlDir = "D:\\Workspaces\\Java\\spring-boot-restful-example\\mybatis\\mapper";
        String author = "zhangxv";
        String parent = "com.example";
        String tables = "user";

        FastAutoGenerator.create(url, username, password).globalConfig(builder -> {
            builder.author(author)
                    // .enableSwagger()
                    .fileOverride().outputDir(outputDir);
        }).packageConfig(builder -> {
            builder.parent(parent).entity("model").service("service").serviceImpl("service.impl").mapper("mapper").xml("mapper.xml").controller("controller").pathInfo(Collections.singletonMap(OutputFile.mapperXml, outputXmlDir));
        }).strategyConfig(builder -> {
            builder.addInclude(tables).addTablePrefix("t_")
                    //Entity配置
                    .entityBuilder().disableSerialVersionUID().enableTableFieldAnnotation()
                    //Controller配置
                    .controllerBuilder().superClass(BaseController.class).enableRestStyle().formatFileName("%sController")
                    //Service配置
                    .serviceBuilder()
                    // .superServiceClass(BaseService.class)
                    // .superServiceImplClass(BaseServiceImpl.class)
                    .formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImp")
                    //Mapper配置
                    .mapperBuilder().superClass(BaseMapper.class).enableMapperAnnotation().enableBaseResultMap()
                    // .cache(MyMapperCache.class)
                    .formatMapperFileName("%sMapper").build();
        }).templateEngine(new FreemarkerTemplateEngine()).execute();

    }
}

