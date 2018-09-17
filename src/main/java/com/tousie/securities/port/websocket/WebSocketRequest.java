package com.tousie.securities.port.websocket;

import com.sonluo.spongebob.spring.server.Attributes;
import com.sonluo.spongebob.spring.server.Client;
import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.Session;
import com.sonluo.spongebob.spring.server.impl.DefaultAttributes;
import com.tousie.securities.model.message.BiRequestMessage;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author sunqian
 */
public class WebSocketRequest implements Request {

    private final BiRequestMessage requestMessage;
    private final javax.websocket.Session webSocketSession;
    private final Session session;

    private final Attributes attributes = new DefaultAttributes();

    WebSocketRequest(BiRequestMessage requestMessage, Session session, javax.websocket.Session webSocketSession) {
        this.requestMessage = requestMessage;
        this.webSocketSession = webSocketSession;
        this.session = session;
    }

    @Nullable
    @Override
    public String getId() {
        return requestMessage.getId();
    }

    @Override
    public String getProtocol() {
        return "webSocket";
    }

    @Override
    public String getUrl() {
        return requestMessage.getUrl();
    }

    @Override
    public String getRemoteAddress() {
        return webSocketSession.getRequestURI().toString();
    }

    @Override
    public Client getClient() {
        return null;
    }

    @Nullable
    @Override
    public Object getContent() {
        return requestMessage.getData();
    }

    @Nullable
    @Override
    public Session getSession(boolean create) {
        return session;
    }

    @Nullable
    @Override
    public Object getAttribute(Object key) {
        return attributes.getAttribute(key);
    }

    @Override
    public void setAttribute(Object key, Object attribute) {
        attributes.setAttribute(key, attribute);
    }

    @Override
    public void removeAttribute(Object key) {
        attributes.removeAttribute(key);
    }

    @Override
    public Map<Object, Object> getAttributes() {
        return attributes.getAttributes();
    }
}
