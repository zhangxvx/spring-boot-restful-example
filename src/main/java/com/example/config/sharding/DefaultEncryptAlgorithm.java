package com.example.config.sharding;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shardingsphere.encrypt.spi.EncryptAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.sharding-sphere.rules.encrypt.encryptors.default-encryptor")
public class DefaultEncryptAlgorithm implements EncryptAlgorithm {
    private static final String AES_KEY = "aes-key-value";
    private static final String AES = "AES";

    private final Properties props = new Properties();

    private byte[] secretKey;

    @SneakyThrows(GeneralSecurityException.class)
    @Override
    public String encrypt(Object plaintext) {
        if (null == plaintext) {
            return null;
        }
        byte[] result = getCipher(Cipher.ENCRYPT_MODE).doFinal(String.valueOf(plaintext).getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printBase64Binary(result);
    }

    @SneakyThrows(GeneralSecurityException.class)
    @Override
    public Object decrypt(String ciphertext) {
        if (null == ciphertext) {
            return null;
        }
        byte[] result = getCipher(Cipher.DECRYPT_MODE).doFinal(DatatypeConverter.parseBase64Binary(ciphertext));
        return new String(result, StandardCharsets.UTF_8);
    }

    @Override
    public void init() {
        log.debug("DefaultEncryptAlgorithm init: props:{}", props);
        secretKey = createSecretKey();
    }

    @Override
    public String getType() {
        return "default-encrypt-algorithm";
    }

    private byte[] createSecretKey() {
        return Arrays.copyOf(DigestUtils.sha1(props.getProperty(AES_KEY)), 16);
    }

    private Cipher getCipher(final int decryptMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher result = Cipher.getInstance(AES);
        result.init(decryptMode, new SecretKeySpec(secretKey, AES));
        return result;
    }
}
