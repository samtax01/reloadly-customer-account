package com.reloadly.customeraccount.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Enable background activities
 * Usage: Add @Async to your method
 */
@EnableAsync
@Configuration
public class AsyncConfig {
    @Bean
    public Executor threadPoolTaskExecutor() {
        return new ConcurrentTaskExecutor(Executors.newCachedThreadPool());
    }
}
