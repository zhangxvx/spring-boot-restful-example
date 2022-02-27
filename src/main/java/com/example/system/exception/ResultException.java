package com.example.system.exception;

import com.example.system.enums.ResultCode;

public class ResultException extends RuntimeException {
    private final ResultCode resultCode;

    public ResultException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
