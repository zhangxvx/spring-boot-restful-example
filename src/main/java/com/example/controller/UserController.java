package com.example.controller;


import com.example.controller.base.BaseController;
import com.example.entity.Response;
import com.example.model.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhangxv
 * @since 2022-01-23
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    UserService userService;

    @RequestMapping("/list")
    @Cacheable(value = "user", keyGenerator = "myKeyGenerator")
    public Response<Object> list() {
        List<User> list = userService.list();
        log.info("user/list::{}", list);
        return Response.success(list);
    }

    @RequestMapping("/id")
    @Cacheable(value = "user", keyGenerator = "myKeyGenerator")
    public Response<Object> id(@RequestParam String id) {
        User user = userService.getById(id);
        log.info("user/id::{}", id);
        return Response.success(user);
    }

    @RequestMapping("/add")
    public Response<Object> add(@RequestParam String name) {
        log.info("user/add::{}", name);
        return userService.save(name);
    }
}
