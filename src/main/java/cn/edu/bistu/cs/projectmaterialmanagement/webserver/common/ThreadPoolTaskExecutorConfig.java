package cn.edu.bistu.cs.projectmaterialmanagement.webserver.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 异步线程池ThreadPoolExecutor 配置类
 */
@Configuration
public class ThreadPoolTaskExecutorConfig {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolTaskExecutorConfig.class);

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        LOGGER.info("创建定时任务调度线程池 start");
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(20);
        threadPoolTaskScheduler.setThreadNamePrefix("examTaskExecutor-");
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        threadPoolTaskScheduler.initialize();
        LOGGER.info("创建定时任务调度线程池 end");
        return threadPoolTaskScheduler;
    }


}
