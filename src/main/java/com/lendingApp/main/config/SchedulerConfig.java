package com.lendingApp.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class to enable scheduling in Spring Boot
 * This allows @Scheduled annotations to work
 */
@Configuration
@EnableScheduling
public class SchedulerConfig {
    // No additional code needed - just enable scheduling
}