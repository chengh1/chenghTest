package com.chengh.db.util.threadPool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: k2-report-service
 * @description:
 * @author: chengh
 * @create: 2019-10-11 10:54
 */
@Configuration
public class MyThreadPoolConfig {

    /**
     * 默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务， 当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
     * 当队列满了，就继续创建线程，当线程数量大于等于maxPoolSize后，开始使用拒绝策略拒绝
     */

    private static final int corePoolSize = 10; // 核心线程数（默认线程数）
    private static final int maxPoolSize = 60; // 最大线程数
    private static final int keepAliveTime = 5; // 允许线程空闲时间（单位：默认为秒）

    /**
     *
     * @return
     */
    @Bean("threadPoolExecutor") // bean的名称，默认为首字母小写的方法名
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new LinkedBlockingDeque(), new MyThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
