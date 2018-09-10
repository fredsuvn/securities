package com.tousie.securities.session;

import com.sonluo.spongebob.spring.server.Session;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private final Map<String, Session> sessionMap = new ConcurrentHashMap<>();


}
