package com.example.system.enums;

import lombok.Getter;

@Getter
public enum AuthType {
    /**
     * JWT
     */
    JWT("JWT"),
    /**
     * JWT
     */
    WHITE_LIST("WHITE_LIST"),
    /**
     * TOKEN
     */
    TOKEN("TOKEN");

    /**
     * 身份认证类型
     */
    private final String type;

    AuthType(String type) {
        this.type = type.toUpperCase();
    }
}

