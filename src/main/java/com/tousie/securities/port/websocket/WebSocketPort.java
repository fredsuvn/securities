package com.tousie.securities.port.websocket;

import com.tousie.securities.common.id.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/service/{url}")
@Component
public class WebSocketPort implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketPort.class);

    private static IdService idService;

    public WebSocketPort() {
        logger.info("NEW INSTANCE!!!!!!");
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("OnOpen: {}, new id: {}.", session.getRequestURI().getPath(), idService.next());
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("onClose");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
//        session.getBasicRemote().
        logger.info("onMessage: {}.", message);
        try {
            session.getBasicRemote().sendText("received: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("OnError", throwable);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        idService = applicationContext.getBean(IdService.class);
    }
}
