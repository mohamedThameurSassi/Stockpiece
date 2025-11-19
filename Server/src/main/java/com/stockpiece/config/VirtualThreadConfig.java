package com.stockpiece.config;

import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;

import java.util.concurrent.Executors;

@Configuration
public class VirtualThreadConfig {
    
    /**
     * Configure Spring to use virtual threads for async operations.
     * Virtual threads are lightweight, managed by the JVM, and allow
     * handling thousands of concurrent operations efficiently.
     * 
     * Note: Virtual threads are enabled globally via spring.threads.virtual.enabled=true
     * in application.yml. This bean provides an explicit executor for @Async operations.
     */
    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
    public AsyncTaskExecutor asyncTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }
}
