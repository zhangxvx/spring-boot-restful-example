package com.example.system.util.security;

import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class AES {
    private static byte[] defaultKey;

    public static String encrypt(String data) {
        return SecureUtil.aes(defaultKey).encryptBase64(data);
    }

    public static String decrypt(String data) {
        return SecureUtil.aes(defaultKey).decryptStr(data);
    }

    @Value("${security.aes.key}")
    private void setDefaultAesKey(String aesKey) {
        defaultKey = aesKey.getBytes(StandardCharsets.UTF_8);
    }
}

