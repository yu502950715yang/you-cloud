package com.you.common.core.handler;

import com.you.common.core.exception.CommonException;
import com.you.common.core.model.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-23 16:49
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义异常
     */
    @ExceptionHandler(value = CommonException.class)
    public R<?> handlerCommonException(CommonException e) {
        log.error(e.getMessage(), e);
        return R.fail(e.getMessage());
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(value = RuntimeException.class)
    public R<?> handlerRuntimeException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}'，发生未知异常", requestUri, e);
        return R.fail(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(value = Exception.class)
    public R<?> handlerException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}'，发生系统异常", requestUri, e);
        return R.fail(e.getMessage());
    }
}
