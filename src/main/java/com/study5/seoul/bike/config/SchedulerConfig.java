package com.study5.seoul.bike.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

// TODO 실시간 데이터 처리를 어떻게 할 지 고민(스케줄러로 생성할 수도 있음) -> 쓰레드풀 관리
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // threadPool 생성
        ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();

        // 코어의 개수 가져오기
        int n = Runtime.getRuntime().availableProcessors();

        // threadPool Size 설정
        threadPool.setPoolSize(n + 1);
        threadPool.initialize();

        // Scheduler -> 설정된 threadPool 사용
        taskRegistrar.setTaskScheduler(threadPool);
    }
}
