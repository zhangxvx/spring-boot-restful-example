package com.example.system.util;

import cn.hutool.core.lang.id.NanoId;
import cn.hutool.core.util.RandomUtil;

import java.security.SecureRandom;

public class NanoIdUtil {
    public static final int DEFAULT_SIZE = 21;
    private static final SecureRandom DEFAULT_NUMBER_GENERATOR = RandomUtil.getSecureRandom();
    private static final char[] DEFAULT_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String nanoid() {
        return NanoId.randomNanoId(DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, DEFAULT_SIZE);
    }
}
