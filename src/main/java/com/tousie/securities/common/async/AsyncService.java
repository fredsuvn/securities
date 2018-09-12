package com.tousie.securities.common.async;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Component
public class AsyncService {

    @Resource
    private ThreadPoolTaskExecutor asyncExecutor;

    public void execute(Runnable task) {
        asyncExecutor.execute(task);
    }

    public void execute(Runnable task, long startTimeout) {
        asyncExecutor.execute(task, startTimeout);
    }

    public Future<?> submit(Runnable task) {
        return asyncExecutor.submit(task);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return asyncExecutor.submit(task);
    }

    public ListenableFuture<?> submitListenable(Runnable task) {
        return asyncExecutor.submitListenable(task);
    }

    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        return asyncExecutor.submitListenable(task);
    }
}
