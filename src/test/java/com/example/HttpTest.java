package com.example;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpRequest;
import com.example.system.entity.SecretData;
import com.example.system.util.RSAUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class HttpTest {
    ObjectMapper objectMapper = new ObjectMapper();
    String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLmtYvor5UiLCJuYW1lIjoiemhhbmdzYW4iLCJpZCI6IjAwMDAiLCJleHAiOjE2NDYyMTY4MTEsImlhdCI6MTY0NTk1NzYxMX0.kX18IsanLf_7cH0VPyRmVYqH7ti7tNVvF1Nf7JPZPnGo2jfXzw8eqGGrRJ3Q7ZcmDh_Re8_w-VJdhAZrbJKUtg";

    String publicClient = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC36YBijCLZdsLbl8dGQvwr+mbnnmtUVe42jzTx3rl4bbpC78I7lus1d0pKisVNsJoIL4UfcTZcW68rrMYXe446HpyIPMaQUAs7GvSumPcrOGjUOSnMv3H/F2TtaH+JZhpX732VAL4aiT62kSkycUcgJ4MEOW84EX9p6KV8j858TwIDAQAB";
    String privateClient = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALfpgGKMItl2wtuXx0ZC/Cv6Zueea1RV7jaPNPHeuXhtukLvwjuW6zV3SkqKxU2wmggvhR9xNlxbryusxhd7jjoenIg8xpBQCzsa9K6Y9ys4aNQ5Kcy/cf8XZO1of4lmGlfvfZUAvhqJPraRKTJxRyAngwQ5bzgRf2nopXyPznxPAgMBAAECgYEAl0GHwYUXtFqkcA4oanT1CQZsU9pK05XI0T5+gN55E77LDfo6eN/qmpJ9m0j+dKLmTA0pI27tEpClRPpYAUAw+975iD4ZhT/Lyr4Eeb6sdXGzYXYrh0EExEfNIXridMZvLeLqq4SGfWp7Mh9/uH8yGF8ry1vBKtUbt3XwUprdfIECQQDbaiAecCWZhvqDprXViuX00PETcYkv9SsbUDvJK50+bSQXUos4VY+F+hk5GOlawBpVy9ZJZ+YyF8BIKy79ZGHRAkEA1pPti1JQ+7Px0O3+hhr+9ezLQmIem0QwPKr2jC2RHJT1mLYwJNgD0T41mfC/pD3El7HFnvYE61yVf/3wRqFkHwJACN5kR/aufmMnUxT0+pmvq9KaO/f13JtjT6pRkFMT+wb8U3kBqZhDwd2XEWjBXeqv5dHkankpo9Kz8fbU4mchUQJBAJMJEsFhSrTvfUhZjQt//C2wcSev7KEgfJ1V2VP89W7Mc+6NCTh1/UeohTpTSj7VRJ1d57khhAVzj84AyGDdvqkCQDfz9q2u+q6+tChPTzLFAXoFgIgXhcpPdQZ2FSEDPngjmBfGSbzdAGVhvddhFWiVexPEVpAccbseFn7E+VWcqI0=";

    String publicServer = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSpjbJBGb5d6v39EoJDqcZs2q2uVlHuLR5RV3qI0gLUDnUyBGGItQPhvDhsgSqs/73u82t69H3H2E6WayFZjd4wp3U0I8LELOoiDqTvYWRxmt0hjyHi9tUxHU9kevswOJfnrrPngU5y3nHSPNsr1y/7WBh84sJiMZkm1fNG6RSPwIDAQAB";
    String privateServer = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJKmNskEZvl3q/f0SgkOpxmzara5WUe4tHlFXeojSAtQOdTIEYYi1A+G8OGyBKqz/ve7za3r0fcfYTpZrIVmN3jCndTQjwsQs6iIOpO9hZHGa3SGPIeL21TEdT2R6+zA4l+eus+eBTnLecdI82yvXL/tYGHziwmIxmSbV80bpFI/AgMBAAECgYB6XlPMvO2oMOHAj4nPsPhii8H6IwOKw2dk4isRmssdFZ6MHT3yLdHB+xm5WrBmOLgCxCVGgUa87ORWqkk5LygNSwpzMIzGdNZupHZ9xi3Hat70NW1SQW9gRd+YqGsgFcGlWiJwWnCeu53B82SKMqVs7D6WORmDiM4JoqNlpMLWAQJBAMLzTgdwDR751k6ugMZEpUBTkv4ifqh9F0vEbJGqSjlQo0rLqZ9lIZbVJWZ1Hj4oVYvJt1dBm/n1E8UW0vHA778CQQDAkrnQ8F8EUzgyieafJ3gULXAX4mw2OnU1zif4Nwi8fzrmrwNBZ2FRDl2WxrV4r+s65TwWT2ErdHmIYXbCdT2BAkEAgq1Izp+DjBH4X4GFfGGjS705uPQyZoqDRRyDkhjN7CFrc8WVC+1kWJDDoyw6e3vZ6/vo06p8/U4UVHZ6K+ciTQJAOwkAE/CMgGvur3edDq9v3WeslHXiTKGRKyR/FlXwxLCQYwXGDrfdmpa31DAok84CY/TAfepp1IOJemi+mpCMgQJBAKOqJ2v54xq6J3jUDpzEtGhfoZ01bqYsADErEKrBMsx0056dWtcuHexshBLVPvNiRDFw+JJ5iuLfq2bS76D4Oo0=";


    @Test
    public void test() throws JsonProcessingException {
        String body = getSecretData(MapUtil.builder().put("key1", "value1").build());
        System.out.println("body = " + body);
        String res = HttpRequest.post("http://127.0.0.1:8080/app/hello/ok4")
                .header("token", token)//头信息，多个头信息多次调用此方法即可
                .header("content-type", "application/json")//头信息，多个头信息多次调用此方法即可
                .header("source", "CESHI")//头信息，多个头信息多次调用此方法即可
                .body(body)
                .timeout(20000)//超时，毫秒
                .execute().body();
        System.out.println("res = " + res);
        Map<String, Object> map = objectMapper.readValue(res, Map.class);
        if (map.get("status").equals("0000")) {
            Map<String, String> data = (Map<String, String>) map.get("data");
            String encryptData = data.get("encryptData");
            String sign = data.get("sign");

            String decrypt = RSAUtil.decryptByPrivateKey(encryptData, privateClient);
            System.out.println("decrypt = " + decrypt);

            boolean verify = RSAUtil.verify(decrypt, sign, publicServer);
            System.out.println("verify = " + verify);
        }
    }

    String getSecretData(Map<Object, Object> map) throws JsonProcessingException {
        String s = objectMapper.writeValueAsString(map);
        String encrypt = RSAUtil.encryptByPublicKey(s, publicServer);
        String sign = RSAUtil.sign(s, privateClient);
        return objectMapper.writeValueAsString(new SecretData(encrypt, sign));
    }
}
