package com.example.system.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SecretData {

    /**
     * 加密数据
     */
    private String encryptData;

    /**
     * 签名
     */
    private String sign;

    public SecretData(String encryptData, String sign) {
        this.encryptData = encryptData;
        this.sign = sign;
    }
}
