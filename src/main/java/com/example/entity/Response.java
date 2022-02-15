package com.example.entity;

import com.example.enums.ResEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Response<T> {
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

    public Response() {
    }


    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setState(true);
        response.setData(data);
        response.setCode(ResEnum.SUCCESS.getCode());
        response.setMsg(ResEnum.SUCCESS.getMsg());
        return response;
    }

    public static <T> Response<T> fail(ResEnum res) {
        Response<T> response = new Response<>();
        response.setState(false);
        response.setData(null);
        response.setCode(res.getCode());
        response.setMsg(res.getMsg());
        return response;
    }
}
