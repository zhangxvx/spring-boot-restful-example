package com.example.system.controller;

import com.example.system.entity.Result;
import com.example.system.enums.ResultCode;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class DefaultErrorController extends AbstractErrorController {
    public DefaultErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping
    public Result<Object> error(HttpServletRequest request) {
        return Result.failure(ResultCode.INTERNAL_ERROR);
    }
}
