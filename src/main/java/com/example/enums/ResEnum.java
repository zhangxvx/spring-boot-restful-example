package com.example.enums;

import lombok.Getter;

@Getter
public enum ResEnum {
    SUCCESS(200, "成功"),
    NOT_FOUND(404, "不存在"),
    ERROR(500, "异常"),
    UNAUTHORIZED(401, "认证失败"),
    RUNTIME_ERROR(500, "运行异常"),
    FAIL(900, "失败");

    private final int code;
    private final String msg;

    ResEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
