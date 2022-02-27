package com.example.system.enums;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS("0000", "成功"),

    /*请求端错误*/
    NOT_FOUND("A0101", "接口不存在"),
    METHOD_NOT_SUPPORT("A0102", "请求不支持"),

    PARAM_NOT_VALID("A0201", "参数非法"),
    PARAM_IS_BLANK("A0202", "参数为空"),
    CONVERT_EXCEPTION("A0203", "参数校验失败"),


    TOKEN_IS_BLANK("A0301", "token为空"),
    TOKEN_NOT_VALID("A0302", "token非法"),
    SOURCE_IS_BLANK("A0303", "source为空"),
    SOURCE_NOT_VALID("A0304", "source非法"),

    /*服务端错误*/
    INTERNAL_ERROR("B0101", "服务异常"),

    DATA_ACCESS_EXCEPTION("B0201", "数据访问异常"),

    /*三方服务错误*/
    SERVICE_UNAVAILABLE("C0101", "服务不可用"),
    SERVICE_TIMEOUT("C0102", "服务超时");

    /**
     * 状态码
     */
    private final String status;
    /**
     * 提示信息
     */
    private final String msg;


    ResultCode(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
