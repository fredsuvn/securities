package com.tousie.securities.port.http.session;

import com.sonluo.spongebob.spring.server.Session;
import com.sonluo.spongebob.spring.utils.SessionUtils;
import com.tousie.securities.common.logging.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

/**
 * @author sunqian
 */
@WebListener
@Component
public class HttpSessionListener implements javax.servlet.http.HttpSessionListener {

    private static final Logger logger = LoggerFactory.getLogger(HttpSessionListener.class);

//    @Resource
//    private HttpPortProperties httpPortProperties;

    @Resource
    private LoggingService loggingService;

//    @Resource
//    private SessionManager sessionManager;

//    private Cache<String, Session> sessionCache;

    @PostConstruct
    private void init() {
//        sessionCache = CacheBuilder.newBuilder()
//                .expireAfterAccess(httpPortProperties.getSessionTimeoutInMinutes(), TimeUnit.MINUTES)
//                .removalListener((RemovalListener<String, Session>) notification -> {
//                    Session session = notification.getValue();
//                    session.close();
//                    loggingService.logDestroySession(logger, session);
//                })
//                .build();
//        sessionManager.registerSessionCache(sessionCache);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession httpSession = se.getSession();
        Session session = SessionUtils.proxySession(new ValidHttpSession(httpSession));
        httpSession.setAttribute(Session.class.getName(), session);
//        sessionCache.put(session.getId(), session);
        loggingService.logCreateSession(logger, session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Session session =
                (Session) se.getSession().getAttribute(Session.class.getName());
        loggingService.logDestroySession(logger, session);
        session.close();
    }
}
