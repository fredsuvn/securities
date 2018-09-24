package com.tousie.securities.port.websocket;

import com.sonluo.spongebob.spring.server.Attributes;
import com.sonluo.spongebob.spring.server.Channel;
import com.sonluo.spongebob.spring.server.Session;
import com.sonluo.spongebob.spring.server.impl.DefaultAttributes;
import com.sonluo.spongebob.spring.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ValidWebSocketSession implements Session {

    private volatile javax.websocket.Session webSocketSession;

    private volatile Attributes attributes = new DefaultAttributes();

    private volatile long createTime = System.currentTimeMillis();
    private volatile long lastAccessTime = createTime;
    private volatile long lastActiveTime = createTime;

    private volatile Map<String, Channel> channelMap = new ConcurrentHashMap<>();
    private volatile Channel defaultChannel;

    ValidWebSocketSession(javax.websocket.Session webSocketSession) {
        this.webSocketSession = webSocketSession;
        defaultChannel = new ValidWebSocketChannel("", webSocketSession);
        channelMap.put(defaultChannel.getId(), defaultChannel);
    }

    @Override
    public String getId() {
        return "ws-" + webSocketSession.getId();
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
    public boolean isOpen() {
        return true;
    }

    @Override
    public void close() {
        try {
            if (webSocketSession != null) {
                webSocketSession.close();
            }
        } catch (IOException e) {
            //Do nothing
        } finally {
            free();
        }
    }

    private void free() {
        webSocketSession = null;
        attributes = null;
        createTime = -1;
        lastAccessTime = -1;
        lastActiveTime = -1;

        if (channelMap != null) {
            channelMap.forEach((name, channel) -> {
                channel.close();
            });
        }

        channelMap = null;
        defaultChannel = null;
    }

    @Override
    public Channel getDefaultChannel() {
        return defaultChannel;
    }

    @Override
    public boolean containsChannel(String channelId) {
        return channelMap.containsKey(channelId);
    }

    @Nullable
    @Override
    public Channel getChannel(String channelId) {
        return channelMap.get(channelId);
    }

    @Override
    public Map<String, Channel> getAllChannels() {
        return Collections.unmodifiableMap(new HashMap<>(channelMap));
    }

    @Override
    public Channel createNewChannel(String channelId) {
        return SessionUtils.proxyChannel(new ValidWebSocketChannel(channelId, webSocketSession));
    }

    @Override
    public Channel getOrCreateChannel(String channelId) {
        return channelMap.computeIfAbsent(channelId, id -> createNewChannel(id));
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

    private static class ValidWebSocketChannel implements Channel {

        private static Logger logger = LoggerFactory.getLogger(ValidWebSocketChannel.class);

        private volatile String id;
        private volatile javax.websocket.Session webSocketSession;

        ValidWebSocketChannel(String id, javax.websocket.Session webSocketSession) {
            this.id = id;
            this.webSocketSession = webSocketSession;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public boolean canPush() {
            return true;
        }

        @Override
        public void push(Object message) {
            try {
                webSocketSession.getBasicRemote().sendObject(message);
            } catch (Exception e) {
                logger.error("Error when push message in channel, id = {}, session id = {}.", getId(), webSocketSession.getId(), e);
            }
        }

        @Override
        public void close() {
            free();
        }

        private void free() {
            id = null;
            webSocketSession = null;
        }
    }
}
