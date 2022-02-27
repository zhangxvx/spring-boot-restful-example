package com.example.system.entity;


import com.example.system.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    /**
     * 状态码
     */
    private String status;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getStatus(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getStatus(), ResultCode.SUCCESS.getMsg(), null);
    }

    public static <T> Result<T> failure(ResultCode code, T data) {
        return new Result<>(code.getStatus(), code.getMsg(), data);
    }

    public static <T> Result<T> failure(ResultCode code) {
        return new Result<>(code.getStatus(), code.getMsg(), null);
    }
}
