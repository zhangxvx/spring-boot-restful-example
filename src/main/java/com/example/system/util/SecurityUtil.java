package com.example.system.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.example.system.enums.SecurityType;
import com.example.system.util.security.AES;
import com.example.system.util.security.RSA;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 加解密工具
 */
public class SecurityUtil {

    /**
     * 解密
     *
     * @param data 密文base64
     * @param key  密钥
     * @param type 算法类型
     * @return 明文
     */
    /*public static String decrypt(String data, String key, SecurityType type) {
        switch (type) {
            case AES:
                return SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8)).decryptStr(data);
            case RSA:
                return SecureUtil.rsa(key, null).decryptStr(data, KeyType.PrivateKey);
            default:
                return null;
        }
    }*/

    /**
     * 加密
     *
     * @param data 明文数据
     * @param key  密钥
     * @param type 算法类型
     * @return 密文base64
     */
    public static String encrypt(String data, String key, SecurityType type) {
        switch (type) {
            case AES:
                return SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8)).encryptBase64(data);
            case RSA:
                return SecureUtil.rsa(null, key).encryptBase64(data, KeyType.PublicKey);
            default:
                return null;
        }
    }

    /**
     * 签名
     *
     * @param data 明文数据
     * @param type 签名算法类型
     * @return 签名base64
     */
/*    public static String sign(String data, String key, SecurityType type) {
        Sign sign;
        switch (type) {
            case MD5withRSA:
                sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, key, null);
                break;
            case SHA256withRSA:
                sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, key, null);
                break;
            default:
                return null;
        }
        byte[] signed = sign.sign(data);
        return Base64.getEncoder().encodeToString(signed);
    }*/

    /**
     * 验签
     *
     * @param data   明文数据
     * @param signed 签名base64
     * @param type   签名算法类型
     * @return 验签结果
     */
    public static boolean verify(String data, String key, String signed, SecurityType type) {
        Sign sign;
        switch (type) {
            case MD5withRSA:
                sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, null, key);
                break;
            case SHA256withRSA:
                sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, null, key);
                break;
            default:
                return false;
        }
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] signedBytes = Base64.getDecoder().decode(signed);
        return sign.verify(dataBytes, signedBytes);
    }


    /**
     * 解密
     *
     * @param data 密文base64
     * @param type 算法类型
     * @return 明文
     */
    public static String decrypt(String data, SecurityType type) {
        switch (type) {
            case AES:
                return AES.decrypt(data);
            case RSA:
                return RSA.decrypt(data, KeyType.PrivateKey);
            default:
                return null;
        }
    }

    /**
     * 加密
     *
     * @param data 明文数据
     * @param type 算法类型
     * @return 密文base64
     */
    /*public static String encrypt(String data, SecurityType type) {
        switch (type) {
            case AES:
                return AES.encrypt(data);
            case RSA:
                return RSA.encrypt(data, SecurityKeyType.PublicKey);
            default:
                return null;
        }
    }*/

    /**
     * 签名
     *
     * @param data 明文数据
     * @param type 签名算法类型
     * @return 签名base64
     */
    public static String sign(String data, SecurityType type) {
        Sign sign;
        switch (type) {
            case MD5withRSA:
                sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, RSA.getDefaultPrivateKey(), null);
                break;
            case SHA256withRSA:
                sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, RSA.getDefaultPrivateKey(), null);
                break;
            default:
                return null;
        }
        byte[] signed = sign.sign(data);
        return Base64.getEncoder().encodeToString(signed);
    }

    /**
     * 验签
     *
     * @param data   明文数据
     * @param signed 签名base64
     * @param type   签名算法类型
     * @return 验签结果
     */
    /*public static boolean verify(String data, String signed, SecurityType type) {
        Sign sign;
        switch (type) {
            case MD5withRSA:
                sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, RSA.getDefaultPrivateKey(), RSA.getDefaultPublicKey());
                break;
            case SHA256withRSA:
                sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA);
                break;
            default:
                return false;
        }
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] signedBytes = Base64.getDecoder().decode(signed);
        return sign.verify(dataBytes, signedBytes);
    }*/
}
