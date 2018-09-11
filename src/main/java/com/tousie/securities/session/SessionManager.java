package com.tousie.securities.session;

import com.google.common.cache.Cache;
import com.sonluo.spongebob.spring.server.Session;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class SessionManager {

    private final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    private final Set<Cache<String, Session>> sessionCaches = ConcurrentHashMap.newKeySet();

    public void registerSessionCache(Cache<String, Session> sessionCache) {
        sessionCaches.add(sessionCache);
    }

    public void forEachSessionCache(Consumer<Cache<String, Session>> action) {
        sessionCaches.forEach(action::accept);
    }
}
