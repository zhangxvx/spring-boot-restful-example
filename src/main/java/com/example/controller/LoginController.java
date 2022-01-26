package com.example.controller;

import com.example.entity.Response;
import com.example.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/token")
    public Response<Object> token(@RequestParam String username) {
        String jwt = JwtUtil.generateToken(username);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return Response.success(map);
    }
}