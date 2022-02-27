package com.example;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class SecureTest {
    @Test
    public void aes() {
        String key = RandomUtil.randomString(32);
        System.out.println("key = " + key);

        AES aes = SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8));
        String base64 = aes.encryptBase64("测试数据");
        String str = aes.decryptStr(base64);
        System.out.println("base64 = " + base64);
        System.out.println("str = " + str);
    }

    @Test
    public void rsa() {
        RSA rsa = SecureUtil.rsa();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        System.out.println("publicKeyBase64 = " + publicKeyBase64);
        System.out.println("privateKeyBase64 = " + privateKeyBase64);

        RSA rsa1 = SecureUtil.rsa(privateKeyBase64, publicKeyBase64);
        String base64 = rsa1.encryptBase64("测试数据", KeyType.PrivateKey);
        String str = rsa1.decryptStr(base64, KeyType.PublicKey);
        System.out.println("base64 = " + base64);
        System.out.println("str = " + str);

        String base641 = rsa1.encryptBase64("测试数据", KeyType.PublicKey);
        String str1 = rsa1.decryptStr(base641, KeyType.PrivateKey);
        System.out.println("base641 = " + base641);
        System.out.println("str1 = " + str1);
    }
}
