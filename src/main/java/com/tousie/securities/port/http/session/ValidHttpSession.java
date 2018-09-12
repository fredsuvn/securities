package com.tousie.securities.port.http.session;

import com.sonluo.spongebob.spring.server.Attributes;
import com.sonluo.spongebob.spring.server.Channel;
import com.sonluo.spongebob.spring.server.Session;
import com.sonluo.spongebob.spring.server.impl.DefaultAttributes;
import com.sonluo.spongebob.spring.utils.SessionUtils;
import com.tousie.securities.port.http.HttpConstants;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

/**
 * @author sunqian
 */
public class ValidHttpSession implements Session {

    private volatile javax.servlet.http.HttpSession httpSession;
    private volatile Attributes attributes = new DefaultAttributes();

    private volatile Channel channel;
    private volatile Map<String, Channel> channelMap;

    public ValidHttpSession(javax.servlet.http.HttpSession httpSession) {
        this.httpSession = httpSession;
        this.channel = SessionUtils.proxyChannel(new ValidHttpChannel(this));
        this.channelMap = Collections.unmodifiableMap(Collections.singletonMap("", channel));
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
    public boolean isOpen() {
        return true;
    }

    @Override
    public void close() {
        try {
            if (httpSession != null) {
                httpSession.invalidate();
            }
        } catch (IllegalStateException e) {
            // If has been invalidated...
        } finally {
            free();
        }
    }

    private void free() {
        httpSession = null;
        attributes = null;
        channel = null;
        channelMap = null;
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

    @Override
    public Map<String, Channel> getAllChannels() {
        return channelMap;
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

    private static class ValidHttpChannel implements Channel {

        private volatile ValidHttpSession session;

        ValidHttpChannel(ValidHttpSession session) {
            this.session = session;
        }

        @Override
        public String getId() {
            return session.getId();
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public boolean canPush() {
            return false;
        }

        @Override
        public void push(Object message) {
            throw new UnsupportedOperationException("Http channel do not support push message.");
        }

        @Override
        public void close() {
            if (session != null) {
                session.close();
            }
            free();
        }

        private void free() {
            session = null;
        }
    }
}
