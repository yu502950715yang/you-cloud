package com.you.common.core.exception;

/**
 * 公共异常类
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-13 15:37
 */
public class CommonException extends RuntimeException {

    public CommonException(Throwable e) {
        super(e.getMessage(), e);
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable e) {
        super(message, e);
    }
}
