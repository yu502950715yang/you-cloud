package com.you.common.core.domain;

import com.you.common.core.constant.Constants;
import com.you.common.core.constant.HttpStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应信息主体
 */
@Getter
@NoArgsConstructor
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = HttpStatus.SUCCESS;

    /**
     * 失败
     */
    public static final int ERROR = HttpStatus.ERROR;

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, ERROR, null);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, ERROR, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, ERROR, null);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, ERROR, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static <T> R<T> okOrFail(boolean success, String msg) {
        int isSuccess = success ? SUCCESS : ERROR;
        return  restResult(null, isSuccess, msg);
    }

    public static <T> R<T> okOrFail(boolean success) {
        return okOrFail(success, Constants.REQUEST_FAIL_MSG);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Boolean isError(R<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret) {
        return R.SUCCESS == ret.getCode();
    }
}
