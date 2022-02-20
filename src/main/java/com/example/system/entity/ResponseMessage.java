package com.example.system.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@NoArgsConstructor
public class ResponseMessage<T> {
    /**
     * 状态
     */
    private boolean state;
    /**
     * 状态码
     */
    private int code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    public ResponseMessage(boolean state, int code, String msg, T data) {
        this.state = state;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseMessage(HttpStatus httpStatus) {
        this(!httpStatus.isError(), httpStatus.value(), httpStatus.getReasonPhrase(), null);
    }

    public ResponseMessage(HttpStatus httpStatus, T data) {
        this(!httpStatus.isError(), httpStatus.value(), httpStatus.getReasonPhrase(), data);
    }

    public static <T> ResponseMessage<Object> ok(T data) {
        return new ResponseMessage<>(HttpStatus.OK, data);
    }

    public static <T> ResponseMessage<Object> ok() {
        return new ResponseMessage<>(HttpStatus.OK);
    }

    public static ResponseMessage<Object> error() {
        return new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
