package com.example.service;

import com.example.entity.Response;
import com.example.model.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangxv
 * @since 2022-02-09
 */
public interface OrderService extends IService<Order> {

    Response<Object> save();
}
