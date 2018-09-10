package com.tousie.securities.service.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class TestAsync {

    private static final Logger logger = LoggerFactory.getLogger(TestAsync.class);

    @Async
    public Future<String> showText(String text) {
        logger.info(">>>>>>>>>>>>>>>>>show text {} at thread {}.", text, Thread.currentThread().getName());
        return new AsyncResult<>(text);
    }
}
