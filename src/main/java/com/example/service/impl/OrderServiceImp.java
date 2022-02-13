package com.example.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Response;
import com.example.enums.ResEnum;
import com.example.mapper.OrderMapper;
import com.example.model.Order;
import com.example.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangxv
 * @since 2022-02-09
 */
@Service
public class OrderServiceImp extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public Response<Object> save() {
        Order order = new Order();
        order.setOrderId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        order.setOrderName(RandomUtil.randomString("赵钱孙李周吴郑王", 1) + RandomUtil.randomString("一二三四五六七八九", 1));
        boolean ok = super.save(order);
        if (ok) {
            return Response.success(order);
        }
        return Response.fail(ResEnum.FAIL);
    }
}
