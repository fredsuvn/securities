package com.tousie.securities.port.http.session;

import com.sonluo.spongebob.spring.server.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession httpSession = se.getSession();
        com.tousie.securities.port.http.session.HttpSession session =
                new com.tousie.securities.port.http.session.HttpSession(httpSession.getId());
        logger.debug("Create http session of which id = {}.", session.getId());
        httpSession.setAttribute(Session.class.getName(), session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        com.tousie.securities.port.http.session.HttpSession session =
                (com.tousie.securities.port.http.session.HttpSession) se.getSession().getAttribute(Session.class.getName());
        session.close();
        logger.debug("Close http session of which id = {}.", session.getId());
    }
}
