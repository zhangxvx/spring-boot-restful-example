package com.example;

import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class HttpTest {
    @Test
    public void test() {
        Map<String, String> header = new HashMap<>();
        String res = HttpRequest.post("http://127.0.0.1:8080/app/pro")
                .header("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJub25lIn0=.eyJhdWQiOiJjbGllbnQiLCJpc3MiOiJzZXJ2ZXIiLCJleHAiOjE2NDMzNDk5NDUsImlhdCI6MTY0MzI2MzU0NSwidXNlcm5hbWUiOiLlvKDkuIkifQ.Rmd2oo6z14tijcnJh-qWyJwo4gAo2c-CkvrJmVJfxnw")//头信息，多个头信息多次调用此方法即可
                .timeout(20000)//超时，毫秒
                .execute().body();
        System.out.println("res = " + res);
    }

    @Test
    public void token() throws JsonProcessingException {
        String str = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjbGllbnQiLCJpc3MiOiJzZXJ2ZXIiLCJleHAiOjE2NDMzNDk5NDUsImlhdCI6MTY0MzI2MzU0NSwidXNlcm5hbWUiOiLlvKDkuIkifQ.Rmd2oo6z14tijcnJh-qWyJwo4gAo2c-CkvrJmVJfxnw";
        String[] split = str.split("\\.");
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        byte[] decode = urlDecoder.decode(split[0]);
        String head = new String(decode);
        System.out.println("head = " + head);

        byte[] decode1 = urlDecoder.decode(split[1]);
        String head1 = new String(decode1);
        System.out.println("head1 = " + head1);


        Map<String, Object> header = new HashMap<>();
        header.put("alg", "none");
        header.put("typ", "JWT");
        ObjectMapper objectMapper = new ObjectMapper();
        String headerJson = objectMapper.writeValueAsString(header);
        System.out.println(Base64.getUrlEncoder().encodeToString(headerJson.getBytes()));
    }
}
