package com.example.system.util;

import com.example.system.enums.AuthType;
import com.example.system.util.auth.JWTUtil;

public class AuthUtil {
    public static boolean verify(String token, AuthType type) {
        switch (type) {
            case JWT:
                return JWTUtil.verify(token);
            case TOKEN:
            default:
                return false;
        }
    }
}
