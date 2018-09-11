package com.tousie.securities.port.http.session;

import com.sonluo.spongebob.spring.server.Attributes;
import com.sonluo.spongebob.spring.server.Channel;
import com.sonluo.spongebob.spring.server.Session;
import com.sonluo.spongebob.spring.server.impl.DefaultAttributes;
import com.tousie.securities.port.http.HttpConstants;
import com.tousie.securities.port.http.HttpPortProperties;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author sunqian
 */
public class HttpSession implements Session {

    private final HttpPortProperties httpPortProperties;

    private final javax.servlet.http.HttpSession httpSession;
    private final Attributes attributes = new DefaultAttributes();

    private final HttpChannel channel;

    public HttpSession(javax.servlet.http.HttpSession httpSession, HttpPortProperties httpPortProperties) {
        this.httpPortProperties = httpPortProperties;
        this.httpSession = httpSession;
        this.channel = new HttpChannel(this);
    }

    @Override
    public String getId() {
        return httpSession.getId();
    }

    @Override
    public long createTime() {
        return httpSession.getCreationTime();
    }

    @Override
    public String getProtocol() {
        return HttpConstants.PROTOCOL;
    }

    @Override
    public long getLastAccessTime() {
        return httpSession.getLastAccessedTime();
    }

    @Override
    public long getLastActiveTime() {
        return httpSession.getLastAccessedTime();
    }

    @Override
    public boolean isAlive() {
        long last = httpSession.getLastAccessedTime();
        long liveTo = last + httpPortProperties.getSessionTimeoutInMinutes() * 60 * 1000;
        return liveTo > System.currentTimeMillis();
    }

    @Override
    public void beat() {
        httpSession.setMaxInactiveInterval(httpSession.getMaxInactiveInterval() * 2);
    }

    @Override
    public void close() {
        try {
            httpSession.invalidate();
        } catch (Exception e) {
            // If has been invalidated...
        }
    }

    @Override
    public Channel getDefaultChannel() {
        return channel;
    }

    @Override
    public boolean containsChannel(String channelId) {
        return channel.getId().equals(channelId);
    }

    @Nullable
    @Override
    public Channel getChannel(String channelId) {
        return containsChannel(channelId) ? channel : null;
    }

    private Collection<Channel> collection = null;

    @Override
    public Collection<Channel> getAllChannels() {
        if (collection == null) {
            collection = Collections.unmodifiableCollection(Collections.singleton(channel));
        }
        return collection;
    }

    @Override
    public Channel createNewChannel(String channelId) {
        throw new UnsupportedOperationException("Http channel do not support create new channel.");
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

    private static class HttpChannel implements Channel {

        private final HttpSession session;

        HttpChannel(HttpSession session) {
            this.session = session;
        }

        @Override
        public String getId() {
            return session.getId();
        }

        @Override
        public long createTime() {
            return session.createTime();
        }

        @Override
        public boolean canPush() {
            return false;
        }

        @Override
        public void push(Object message) {
            throw new UnsupportedOperationException("Http channel do not support push message.");
        }
    }
}
