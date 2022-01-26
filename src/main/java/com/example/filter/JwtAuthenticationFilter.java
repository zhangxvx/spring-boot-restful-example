package com.example.filter;

import com.example.entity.Response;
import com.example.enums.ResEnum;
import com.example.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@WebFilter(urlPatterns = ("/pro/*"))
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        try {
            //检查jwt令牌, 如果令牌不合法或者过期, 里面会直接抛出异常, 下面的catch部分会直接返回
            JwtUtil.validateToken(token);
        } catch (Exception e) {
            log.error("JwtHandlerInterceptor", e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            String userJson = new ObjectMapper().writeValueAsString(Response.fail(ResEnum.UNAUTHORIZED));
            OutputStream out = response.getOutputStream();
            out.write(userJson.getBytes(StandardCharsets.UTF_8));
            out.flush();
            return;
        }
        filterChain.doFilter(request, response);
    }
}
