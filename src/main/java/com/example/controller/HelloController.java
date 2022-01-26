package com.example.controller;

import com.example.entity.Response;
import com.example.enums.ResEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

    @GetMapping(value = "/ok")
    public Response<Object> ok() {
        return Response.success("ok");
    }

    @GetMapping(value = "/fail")
    public Response<Object> fail() {
        return Response.fail(ResEnum.FAIL);
    }

    @RequestMapping(value = "/ex")
    public Response<Object> ex() throws Exception {
        throw new Exception("测试异常");
    }
}
