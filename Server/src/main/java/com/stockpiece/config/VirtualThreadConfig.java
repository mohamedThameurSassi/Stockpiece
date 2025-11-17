package com.stockpiece.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
public class VirtualThreadConfig {
    
    /**
     * Configure Spring to use virtual threads for async operations
     * Virtual threads are lightweight, managed by the JVM, and allow
     * handling thousands of concurrent operations efficiently.
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("vthread-");
        executor.setVirtualThreads(true);
        executor.setCorePoolSize(0);
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        executor.setQueueCapacity(0);
        executor.initialize();
        return executor;
    }
}
