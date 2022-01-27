package com.example.filter;

import com.auth0.jwt.interfaces.Claim;
import com.example.entity.Response;
import com.example.enums.ResEnum;
import com.example.util.JwtUtil;
import com.example.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (token == null) {
            log.warn("JWT authentication token is null");
            ResponseUtil.responseErrorJson(response, Response.fail(ResEnum.UNAUTHORIZED));
            return;
        }
        try {
            //检查jwt令牌, 如果令牌不合法或者过期, 里面会直接抛出异常, 下面的catch部分会直接返回
            Map<String, Claim> claimMap = JwtUtil.validateToken(token);
        } catch (Exception e) {
            log.error("JwtAuthenticationFilter error", e);
            ResponseUtil.responseErrorJson(response, Response.fail(ResEnum.UNAUTHORIZED));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
