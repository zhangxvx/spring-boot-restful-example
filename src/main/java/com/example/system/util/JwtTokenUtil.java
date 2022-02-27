package com.example.system.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {
    public static final long JWT_TOKEN_VALIDITY = 3 * 24 * 60 * 60;

    private static final String SECRET = "TcUF7CC8T3txmfQ38pYsQ3KY";

    public static String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public static Claims verify(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("id", "0000");
        String token = generateToken(map, "测试");
        System.out.println(token);
        Claims claims = verify(token);
        System.out.println(claims);
    }
}
