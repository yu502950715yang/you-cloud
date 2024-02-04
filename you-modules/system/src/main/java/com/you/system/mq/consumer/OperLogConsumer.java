package com.you.system.mq.consumer;

import com.you.system.api.domain.model.SysOperLog;
import com.you.system.service.SysOperLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class OperLogConsumer {

    private final SysOperLogService operLogService;

    public OperLogConsumer(SysOperLogService operLogService) {
        this.operLogService = operLogService;
    }

    @Bean
    public Consumer<SysOperLog> operLogMq() {
        return sysOperLog -> {
            try {
                if (sysOperLog != null) {
                    operLogService.save(sysOperLog);
                }
            } catch (Exception e) {
                log.error("消费操作日志失败：{}", e.getMessage());
            }
        };
    }
}
