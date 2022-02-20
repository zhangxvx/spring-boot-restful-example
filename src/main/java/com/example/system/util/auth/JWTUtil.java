package com.example.system.util.auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
    private static String defaultSecret;

    public static String generateToken(int validDay) {
        Date iatDate = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, validDay);
        Date expiresDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        /*
         iss: jwt签发者
         sub: jwt所面向的用户
         aud: 接收jwt的一方
         exp: jwt的过期时间，这个过期时间必须要大于签发时间
         nbf: 定义在什么时间之前，该jwt都是不可用的.
         iat: jwt的签发时间
         jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
         */
        return JWT.create().withHeader(map) // header
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(defaultSecret));
    }

    public static boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(defaultSecret)).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Value("${security.jwt.secret}")
    private void setDefaultSecret(String secret) {
        defaultSecret = secret;
    }
}
