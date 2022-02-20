package com.example.system.config.sharding;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
@Setter
@Getter
public class ShardingSphereDefaultAESEncrypt {

    private static final String AES_KEY = "aes-key-value";

    private Properties props = new Properties();

    private byte[] secretKey;

    public static void main(String[] args) throws Exception {
        ShardingSphereDefaultAESEncrypt encryptUtil = new ShardingSphereDefaultAESEncrypt();
        Properties props = new Properties();
        props.setProperty(AES_KEY, "123456abc");
        encryptUtil.setProps(props);
        encryptUtil.init();
        String encrypt = encryptUtil.encrypt("吴二");
        String decrypt = encryptUtil.decrypt(encrypt);
        log.info("encrypt:{}. decrypt:{}. equals:{}", encrypt, decrypt, "YLaMzayN544Scnnm4cpuzA==".equals(encrypt));
    }

    public void init() {
        secretKey = createSecretKey();
    }

    private byte[] createSecretKey() {
        return Arrays.copyOf(DigestUtils.sha1(props.getProperty(AES_KEY)), 16);
    }

    public String encrypt(final Object plaintext) throws Exception {
        if (null == plaintext) {
            return null;
        }
        byte[] result = getCipher(Cipher.ENCRYPT_MODE).doFinal(String.valueOf(plaintext).getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printBase64Binary(result);
    }


    public String decrypt(final String ciphertext) throws Exception {
        if (null == ciphertext) {
            return null;
        }
        byte[] result = getCipher(Cipher.DECRYPT_MODE).doFinal(DatatypeConverter.parseBase64Binary(ciphertext));
        return new String(result, StandardCharsets.UTF_8);
    }

    private Cipher getCipher(final int decryptMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher result = Cipher.getInstance(getType());
        result.init(decryptMode, new SecretKeySpec(secretKey, getType()));
        return result;
    }

    private String getType() {
        return "AES";
    }
}
