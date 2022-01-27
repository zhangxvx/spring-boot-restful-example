package com.example.filter;

import com.example.entity.Response;
import com.example.enums.ResEnum;
import com.example.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class GlobalExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("ExceptionFilter error", e);
            ResponseUtil.responseErrorJson(response, Response.fail(ResEnum.ERROR));
        }
    }
}
