package com.you.system.config;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * 消费日志线程池
     */
    @Bean("operLogThreadPool")
    public Executor threadPoolExecutor() {
        int corePoolSize = 5; // 核心线程数
        int maxPoolSize = 10; // 最大线程数
        long keepAliveTime = 60L; // 线程空闲时间
        int queueCapacity = 25; // 队列容量

        return new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueCapacity),
                // 饱和策略 当线程池和队列都满时，任务将由调用者线程直接执行 适用于日志记录任务量不大的场景
                // DiscardOldestPolicy 当线程池和队列都满时，队列最老的任务会被丢弃 适用于日志记录任务量较大且容忍部分日志丢失的场景
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
