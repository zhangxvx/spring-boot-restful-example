package com.example.util;

import com.example.entity.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ResponseUtil {
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=utf-8";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void responseErrorJson(HttpServletResponse response, Response<Object> res) throws IOException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String userJson = objectMapper.writeValueAsString(res);
        OutputStream out = response.getOutputStream();
        out.write(userJson.getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
}
