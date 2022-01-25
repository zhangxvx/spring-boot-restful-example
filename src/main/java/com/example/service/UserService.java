package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Response;
import com.example.model.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhangxv
 * @since 2022-01-23
 */
public interface UserService extends IService<User> {
    Response<Object> save(String name);
}
