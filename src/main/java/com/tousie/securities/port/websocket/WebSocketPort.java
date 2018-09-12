package com.tousie.securities.port.websocket;

import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.Server;
import com.sonluo.spongebob.spring.utils.SessionUtils;
import com.tousie.securities.common.logging.LoggingService;
import com.tousie.securities.common.mdc.MdcService;
import com.tousie.securities.common.message.MessageService;
import com.tousie.securities.common.status.StatusEnum;
import com.tousie.securities.model.message.BiRequestMessage;
import com.tousie.securities.model.message.BiResponseMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/service/{url}", encoders = WebSocketEncoder.class)
@Component
public class WebSocketPort implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketPort.class);

//    private static IdService idService;

    private static MdcService mdcService;

    private static WebSocketSessionManager webSocketSessionManager;

    private static LoggingService loggingService;

    private static MessageService messageService;

    private static Server server;

    private static WebSocketPortProperties webSocketPortProperties;

    @OnOpen
    public void onOpen(Session webSocketSession) {
        com.sonluo.spongebob.spring.server.Session session
                = SessionUtils.proxySession(new ValidWebSocketSession(webSocketSession));
        webSocketSessionManager.addSession(session);
        loggingService.logCreateSession(logger, session);
    }

    @OnClose
    public void onClose(Session webSocketSession) {
        webSocketSessionManager.closeSession(webSocketSession.getId());
        loggingService.logDestroySession(logger, webSocketSession.getId(), "webSocket");
    }

    @OnError
    public void onError(Session webSocketSession, Throwable throwable) {
        logger.error("WebSocket session threw an exception, id = {}.", webSocketSession.getId(), throwable);
        onClose(webSocketSession);
    }

    @OnMessage
    public void onMessage(String message, Session webSocketSession) {
        mdcService.putRequestId();
        com.sonluo.spongebob.spring.server.Session session
                = webSocketSessionManager.getSession(webSocketSession.getId());
        if (session == null) {
            logger.error("Received webSocket message but session not found.");
            return;
        }
        BiRequestMessage requestMessage = null;
        try {
            requestMessage = messageService.toBiRequestMessage(message);
        } catch (Exception e) {
            logger.error("Request message error: {}",
                    StringUtils.abbreviate(message, webSocketPortProperties.getMaxMessageLogWidth()), e);
            pushMessage(session, messageService.toBiResponseMessage(null, StatusEnum.INTERNAL_ERROR, null));
            mdcService.removeRequestId();
            return;
        }
        if (requestMessage.getUrl() == null) {
            pushMessage(session, messageService.toBiResponseMessage(null,
                    StatusEnum.SERVICE_NOT_FOUND.getCode(),
                    StatusEnum.SERVICE_NOT_FOUND.getDesc() + ": " + null,
                    null));
            mdcService.removeRequestId();
            return;
        }
        Request request = new WebSocketRequest(requestMessage, session, webSocketSession);
        Object result = server.doService(request);
        if (result != null) {
            BiResponseMessage response = messageService.toBiResponseMessage(requestMessage.getId(), result);
            pushMessage(session, response);
        }
        mdcService.removeRequestId();
    }

    private void pushMessage(com.sonluo.spongebob.spring.server.Session session, Object message) {
        try {
            session.getDefaultChannel().push(message);
        } catch (Exception e) {
            logger.error("Push message error!", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        idService = applicationContext.getBean(IdService.class);
        mdcService = applicationContext.getBean(MdcService.class);
        webSocketSessionManager = applicationContext.getBean(WebSocketSessionManager.class);
        loggingService = applicationContext.getBean(LoggingService.class);
        messageService = applicationContext.getBean(MessageService.class);
        server = applicationContext.getBean(Server.class);
        webSocketPortProperties = applicationContext.getBean(WebSocketPortProperties.class);
    }
}
