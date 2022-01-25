package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Response;
import com.example.enums.ResEnum;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangxv
 * @since 2022-01-23
 */
@Slf4j
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @Transactional
    public Response<Object> save(String name) {
        User user = new User();
        user.setName(name);
        boolean ok = super.save(user);
        if (ok) {
            return Response.success(user);
        }
        return Response.fail(ResEnum.FAIL);
    }
}
