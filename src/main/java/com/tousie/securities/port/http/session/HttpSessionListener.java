package com.tousie.securities.port.http.session;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.sonluo.spongebob.spring.server.Session;
import com.tousie.securities.common.logging.LoggingService;
import com.tousie.securities.port.http.HttpPortProperties;
import com.tousie.securities.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import java.util.concurrent.TimeUnit;

/**
 * @author sunqian
 */
@WebListener
@Component
public class HttpSessionListener implements javax.servlet.http.HttpSessionListener {

    private static final Logger logger = LoggerFactory.getLogger(HttpSessionListener.class);

    @Resource
    private HttpPortProperties httpPortProperties;

    @Resource
    private LoggingService loggingService;

    @Resource
    private SessionManager sessionManager;

    private Cache<String, Session> sessionCache;

    @PostConstruct
    private void init() {
        sessionCache = CacheBuilder.newBuilder()
                .expireAfterAccess(httpPortProperties.getSessionTimeoutInMinutes(), TimeUnit.MINUTES)
                .removalListener((RemovalListener<String, Session>) notification -> {
                    Session session = notification.getValue();
                    session.close();
                    loggingService.logDestroySession(logger, session);
                })
                .build();
        sessionManager.registerSessionCache(sessionCache);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession httpSession = se.getSession();
        com.tousie.securities.port.http.session.HttpSession session =
                new com.tousie.securities.port.http.session.HttpSession(httpSession, httpPortProperties);
        httpSession.setAttribute(Session.class.getName(), session);
        sessionCache.put(session.getId(), session);
        loggingService.logCreateSession(logger, session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
//        com.tousie.securities.port.http.session.HttpSession session =
//                (com.tousie.securities.port.http.session.HttpSession) se.getSession().getAttribute(Session.class.getName());
//        session.close();
    }
}
