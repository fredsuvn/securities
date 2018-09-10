package com.tousie.securities.common.task;

import com.tousie.securities.common.mdc.MdcService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@Configuration
@EnableAsync
@EnableScheduling
public class TaskConfiguration {

    @Value("${async.corePoolSize}")
    private int corePoolSize;

    @Value("${async.maxPoolSize}")
    private int maxPoolSize;

    @Value("${async.queueCapacity}")
    private int queueCapacity;

    @Value("${async.keepAliveSeconds}")
    private int keepAliveSeconds;

    @Resource
    private MdcService mdcService;

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new AsyncExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

    private class AsyncExecutor extends ThreadPoolTaskExecutor {

        @Override
        public void execute(Runnable task) {
            String requestId = mdcService.getRequestId();
            super.execute(() -> {
                mdcService.putRequestId(requestId);
                task.run();
                mdcService.removeRequestId();
            });
        }

        @Override
        public void execute(Runnable task, long startTimeout) {
            String requestId = mdcService.getRequestId();
            super.execute(() -> {
                mdcService.putRequestId(requestId);
                task.run();
                mdcService.removeRequestId();
            }, startTimeout);
        }

        @Override
        public Future<?> submit(Runnable task) {
            String requestId = mdcService.getRequestId();
            return super.submit(() -> {
                mdcService.putRequestId(requestId);
                task.run();
                mdcService.removeRequestId();
            });
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            String requestId = mdcService.getRequestId();
            return super.submit(() -> {
                mdcService.putRequestId(requestId);
                T result = task.call();
                mdcService.removeRequestId();
                return result;
            });
        }

        @Override
        public ListenableFuture<?> submitListenable(Runnable task) {
            String requestId = mdcService.getRequestId();
            return super.submitListenable(() -> {
                mdcService.putRequestId(requestId);
                task.run();
                mdcService.removeRequestId();
            });
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
            String requestId = mdcService.getRequestId();
            return super.submitListenable(() -> {
                mdcService.putRequestId(requestId);
                T result = task.call();
                mdcService.removeRequestId();
                return result;
            });
        }
    }
}
