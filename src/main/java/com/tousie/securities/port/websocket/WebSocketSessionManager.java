package com.tousie.securities.port.websocket;

import com.sonluo.spongebob.spring.server.Session;
import com.tousie.securities.common.async.SchedulingConfiguration;
import com.tousie.securities.common.mdc.MdcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author sunqian
 */
@Component
public class WebSocketSessionManager {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketSessionManager.class);

    private final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    public void addSession(Session session) {
        sessionMap.put(session.getId(), session);
    }

    @Nullable
    public Session getSession(String id) {
        return sessionMap.get(id);
    }

    public void removeSession(String id) {
        sessionMap.remove(id);
    }

    public void closeSession(String id) {
        Session session = getSession(id);
        if (session != null) {
            session.close();
            sessionMap.remove(id);
        }
    }

    void forEachSession(Consumer<Session> action) {
        sessionMap.forEach((id, session) -> action.accept(session));
    }
}
