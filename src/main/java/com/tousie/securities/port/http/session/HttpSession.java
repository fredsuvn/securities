package com.tousie.securities.port.http.session;

import com.sonluo.spongebob.spring.server.AbstractSession;
import com.sonluo.spongebob.spring.server.Attributes;
import com.sonluo.spongebob.spring.server.impl.DefaultAttributes;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author sunqian
 */
public class HttpSession extends AbstractSession {

    private final String id;
    private final Attributes attributes = new DefaultAttributes();

    public HttpSession(String id) {
        this.id = "http-" + id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean canPush() {
        return false;
    }

    @Override
    public void push(Object message) {
        throw new UnsupportedOperationException("Http session do not support push message.");
    }

    @Override
    public void close() {
        for (Runnable action : destroyActions) {
            action.run();
        }
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
}
