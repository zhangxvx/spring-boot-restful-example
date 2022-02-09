package com.example.controller;


import com.example.controller.base.BaseController;
import com.example.entity.Response;
import com.example.model.Order;
import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
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
 * @since 2022-02-09
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Resource
    OrderService orderService;

    @RequestMapping("/list")
    public Response<Object> list() {
        List<Order> list = orderService.list();
        log.info("order/list::{}", list);
        return Response.success(list);
    }

    @RequestMapping("/id")
    public Response<Object> id(@RequestParam String id) {
        Order order = orderService.getById(id);
        log.info("order/id::{}", id);
        return Response.success(order);
    }

    @RequestMapping("/add")
    public Response<Object> add() {
        log.info("order/add");
        return orderService.save();
    }
}
