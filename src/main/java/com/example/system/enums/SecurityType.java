package com.example.system.enums;

import lombok.Getter;

/**
 * 加密类型
 */
@Getter
public enum SecurityType {
    /**
     * 对称加密，AES
     */
    AES("AES"),
    /**
     * 非对称加密，RSA
     */
    RSA("RSA"),
    /**
     * 摘要算法，MD5
     */
    MD5("MD5"),
    /**
     * 摘要算法，SHA-256
     */
    SHA256("SHA-256"),
    /**
     * 签名算法，MD5withRSA
     */
    MD5withRSA("MD5withRSA"),
    /**
     * 签名算法，MD5withRSA
     */
    SHA256withRSA("SHA256withRSA");

    /**
     * 加密类型
     */
    private final String type;

    SecurityType(String type) {
        this.type = type.toUpperCase();
    }
}

