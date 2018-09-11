package com.tousie.securities.port.websocket;

import com.sonluo.spongebob.spring.server.Attributes;
import com.sonluo.spongebob.spring.server.Channel;
import com.sonluo.spongebob.spring.server.Session;
import com.sonluo.spongebob.spring.server.impl.DefaultAttributes;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class WebSocketSession implements Session {

    private final javax.websocket.Session webSocketSession;

    private final Attributes attributes = new DefaultAttributes();

    private final long createTime = System.currentTimeMillis();
    private long lastAccessTime = createTime;
    private long lastActiveTime = createTime;

    WebSocketSession(javax.websocket.Session webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    @Override
    public String getId() {
        return webSocketSession.getId();
    }

    @Override
    public long createTime() {
        return createTime;
    }

    @Override
    public String getProtocol() {
        return "webSocket";
    }

    @Override
    public long getLastAccessTime() {
        return lastAccessTime;
    }

    @Override
    public long getLastActiveTime() {
        return lastActiveTime;
    }

    @Override
    public boolean isAlive() {
        return webSocketSession.isOpen();
    }

    @Override
    public void close() {
        try {
            webSocketSession.close();
        } catch (IOException e) {
        }
    }

    @Override
    public Channel getDefaultChannel() {
        return null;
    }

    @Override
    public boolean containsChannel(String channelId) {
        return false;
    }

    @Nullable
    @Override
    public Channel getChannel(String channelId) {
        return null;
    }

    @Override
    public Collection<Channel> getAllChannels() {
        return null;
    }

    @Override
    public Channel createNewChannel(String channelId) {
        return null;
    }

    @Nullable
    @Override
    public Object getAttribute(String name) {
        return attributes.getAttribute(name);
    }

    @Override
    public void setAttribute(String name, Object attribute) {
        attributes.setAttribute(name, attribute);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.removeAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes.getAttributes();
    }

    private static class WebSocketChannel implements Channel{

        private final String id;
        private final WebSocketSession session;


        @Override
        public String getId() {
            return id;
        }

        @Override
        public boolean isOpen() {
//            return session.;
        }

        @Override
        public boolean canPush() {
            return false;
        }

        @Override
        public void push(Object message) {

        }
    }
}
