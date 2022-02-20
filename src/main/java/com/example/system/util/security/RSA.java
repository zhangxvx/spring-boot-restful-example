package com.example.system.util.security;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RSA {
    private static String defaultPublicKey;
    private static String defaultPrivateKey;

    public static String encrypt(String data, KeyType keyType) {
        return SecureUtil.rsa(defaultPrivateKey, defaultPublicKey).encryptBase64(data, keyType);
    }

    public static String decrypt(String data, KeyType keyType) {
        return SecureUtil.rsa(defaultPrivateKey, defaultPublicKey).decryptStr(data, keyType);
    }

    public static String getDefaultPrivateKey() {
        return defaultPrivateKey;
    }

    public static String getDefaultPublicKey() {
        return defaultPublicKey;
    }

    @Value("${security.rsa.public-key}")
    private void setPublicKey(String publicKey) {
        defaultPublicKey = publicKey;
    }

    @Value("${security.rsa.private-key}")
    private void setPrivateKey(String privateKey) {
        defaultPrivateKey = privateKey;
    }
}