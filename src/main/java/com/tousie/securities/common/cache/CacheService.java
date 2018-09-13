package com.tousie.securities.common.cache;

import com.google.common.cache.*;
import com.sonluo.spongebob.spring.server.BeanOperator;
import com.sonluo.spongebob.spring.server.impl.DefaultBeanOperator;
import com.tousie.securities.common.logging.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author sunqian
 */
@Component
public class CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

    @Resource
    private CacheProperties cacheProperties;

    @Resource
    private LoggingService loggingService;

    private LoadingCache<Object, Object> cache;
    private BeanOperator beanOperator = DefaultBeanOperator.INSTANCE;


    @PostConstruct
    private void init() {
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(cacheProperties.getExpireAfterAccessInMillis(), TimeUnit.MILLISECONDS)
                .maximumSize(cacheProperties.getMaximumSize())
                .concurrencyLevel(cacheProperties.getConcurrencyLevel())
                .removalListener((RemovalListener<Object, Object>) notification -> {
//                    Object object = notification.getValue();
                    loggingService.logRemoveCache(logger, String.valueOf(notification.getKey()));
                })
                .build(new CacheLoader<Object, Object>() {
                    @Override
                    public Object load(Object key) throws Exception {
                        return null;
                    }
                });
    }

    @Nullable
    public <T> T getCache(Object key) {
        return (T) cache.getIfPresent(key);
    }

    @Nullable
    public <T> T getCache(Object key, Class<T> type) {
        Object obj = cache.getIfPresent(key);
        if (obj == null) {
            return null;
        }
        return beanOperator.convert(obj, type);
    }

    @Nullable
    public <T> T getCache(Object key, Type type) {
        Object obj = cache.getIfPresent(key);
        if (obj == null) {
            return null;
        }
        return beanOperator.convert(obj, type);
    }

    public void putCache(Object key, Object value) {
        cache.put(key, value);
    }

    public <T> T getCache(Object key, Supplier<T> supplier) {
        try {
            return (T) cache.get(key, supplier::get);
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T> T getCache(Object key, Supplier<T> supplier, Class<T> type) {
        try {
            return beanOperator.convert(cache.get(key, supplier::get), type);
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T> T getCache(Object key, Supplier<T> supplier, Type type) {
        try {
            return beanOperator.convert(cache.get(key, supplier::get), type);
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }
}
