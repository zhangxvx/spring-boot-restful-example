package com.example.system.util;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class RSAUtil {
    private static String defaultPublicKey;
    private static String defaultPrivateKey;

    public static String encrypt(String data, KeyType keyType) {
        return SecureUtil.rsa(defaultPrivateKey, defaultPublicKey).encryptBase64(data, keyType);
    }

    public static String decrypt(String data, KeyType keyType) {
        return SecureUtil.rsa(defaultPrivateKey, defaultPublicKey).decryptStr(data, keyType);
    }

    public static String encryptByPublicKey(String data) {
        return SecureUtil.rsa(null, defaultPublicKey).encryptBase64(data, KeyType.PublicKey);
    }

    public static String decryptByPrivateKey(String data) {
        return SecureUtil.rsa(defaultPrivateKey, null).decryptStr(data, KeyType.PrivateKey);
    }

    public static String encryptByPublicKey(String data, String publicKey) {
        return SecureUtil.rsa(null, publicKey).encryptBase64(data, KeyType.PublicKey);
    }

    public static String decryptByPrivateKey(String data, String privateKey) {
        return SecureUtil.rsa(privateKey, null).decryptStr(data, KeyType.PrivateKey);
    }

    public static String sign(String data) {
        return SecureUtil.sign(SignAlgorithm.SHA256withRSA, defaultPrivateKey, null).signHex(data);
    }

    public static String sign(String data, String privateKey) {
        return SecureUtil.sign(SignAlgorithm.SHA256withRSA, privateKey, null).signHex(data);
    }

    public static boolean verify(String data, String signed) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] hex = HexUtil.decodeHex(signed);
        return SecureUtil.sign(SignAlgorithm.SHA256withRSA, null, defaultPublicKey).verify(bytes, hex);
    }

    public static boolean verify(String data, String signed, String publicKey) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] hex = HexUtil.decodeHex(signed);
        return SecureUtil.sign(SignAlgorithm.SHA256withRSA, null, publicKey).verify(bytes, hex);
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