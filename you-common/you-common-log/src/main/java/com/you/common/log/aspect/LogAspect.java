package com.you.common.log.aspect;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.you.auth.utils.LoginUtils;
import com.you.common.core.exception.CommonException;
import com.you.common.core.utils.IpUtils;
import com.you.common.log.annotation.OperLog;
import com.you.common.log.enums.OperLogTypEnum;
import com.you.common.mq.enums.TopicEnum;
import com.you.common.mq.producer.ProducerMessage;
import com.you.system.api.domain.model.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LogAspect {

    private static final ThreadLocal<StopWatch> TIME_THREADLOCAL = new TransmittableThreadLocal<>();
    private static final ThreadLocal<String> PARAM_THREADLOCAL = new TransmittableThreadLocal<>();
    private final ProducerMessage producerMessage;

    public LogAspect(ProducerMessage producerMessage) {
        this.producerMessage = producerMessage;
    }

    //切点
    @Pointcut("execution(* com.you..*.*Controller.*(..)) && @annotation(com.you.common.log.annotation.OperLog)")
    public void logPointCut() {
    }

    @Before(value = "@annotation(operLog)")
    public void boBefore(JoinPoint joinPoint, OperLog operLog) {
        StopWatch stopWatch = new StopWatch();
        TIME_THREADLOCAL.set(stopWatch);
        PARAM_THREADLOCAL.set(StringUtils.substring(JSON.toJSONString(getParams(joinPoint)), 0, 2000));
        stopWatch.start();
    }

    @AfterReturning(value = "logPointCut() && @annotation(operLog)", returning = "jsonResult")
    public void afterReturning(JoinPoint joinPoint, OperLog operLog, Object jsonResult) {
        try {
            //一般日志
            SysOperLog sysLog = wrapSysLog(joinPoint, operLog);
            // 设置消耗时间
            StopWatch stopWatch = TIME_THREADLOCAL.get();
            stopWatch.stop();
            sysLog.setStatus(0);
            sysLog.setOperParam(PARAM_THREADLOCAL.get());
            sysLog.setReponseTime(stopWatch.getTotalTimeMillis());
            if (!OperLogTypEnum.noRecordResponse(operLog.type())) {
                sysLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
            }
            //发送mq消息
            producerMessage.send(TopicEnum.OPER_LOG.getTopicName(), sysLog);
        } catch (Exception e) {
            log.error("LogAspect afterLog error", e);
        } finally {
            TIME_THREADLOCAL.remove();
            PARAM_THREADLOCAL.remove();
        }
    }


    //异常通知
    @AfterThrowing(value = "execution(* com.you..*.*Controller.*(..))", throwing = "e")
    public void throwingLog(JoinPoint joinPoint, Exception e) {
        try {
            if (e instanceof CommonException) {
                return;
            }
            //一般日志
            SysOperLog sysLog = wrapSysLog(joinPoint, null);
            // 设置消耗时间
            StopWatch stopWatch = TIME_THREADLOCAL.get();
            if (null != stopWatch) {
                stopWatch.stop();
                sysLog.setReponseTime(stopWatch.getTotalTimeMillis());
            }
            sysLog.setStatus(1);
            sysLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            //发送mq消息
            producerMessage.send(TopicEnum.OPER_LOG.getTopicName(), sysLog);
        } catch (Exception ex) {
            log.error("LogAspect afterLog error", ex);
        } finally {
            TIME_THREADLOCAL.remove();
            PARAM_THREADLOCAL.remove();
        }
    }

    /**
     * 封装SysLog对象
     */
    public SysOperLog wrapSysLog(JoinPoint joinPoint, OperLog operLog) {
        SysOperLog sysLog = new SysOperLog();
        //获取请求响应对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            //请求方式
            sysLog.setRequestMethod(request.getMethod());
            //请求地址
            sysLog.setOperUrl(request.getRequestURI());
            //主机ip
            sysLog.setOperIp(IpUtils.getIpAddr());
        }
        if (operLog != null) {
            //模块标题
            sysLog.setTitle(operLog.title());
            // 业务类型
            sysLog.setBusinessType(operLog.type().getMsg());
            //操作类别
            sysLog.setOperatorType(operLog.operatorType().getCode());
        }
        //方法名称
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        sysLog.setMethod(methodName);
        //操作人员
        sysLog.setOperName(LoginUtils.getLoginUserName());
        //操作时间
        sysLog.setOperTime(LocalDateTime.now());
        return sysLog;
    }

    private String getParams(JoinPoint pjp) {
        return Arrays.stream(pjp.getArgs()).map(item -> {
            if (item == null ||
                    item instanceof Errors ||
                    item instanceof HttpRequest ||
                    item instanceof HttpServletRequest ||
                    item instanceof HttpServletResponse ||
                    item instanceof HttpSession ||
                    "class org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile".equals(item.getClass().toString())) {
                return "";
            }
            return JSONObject.toJSONString(item);
        }).filter(item -> !"".equals(item)).collect(Collectors.joining(","));
    }
}
