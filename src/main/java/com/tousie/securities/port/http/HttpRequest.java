package com.tousie.securities.port.http;

import com.sonluo.spongebob.spring.server.Attributes;
import com.sonluo.spongebob.spring.server.Client;
import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.Session;
import com.sonluo.spongebob.spring.server.impl.DefaultAttributes;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author sunqian
 */
public class HttpRequest implements Request {


    private final String url;
    private final Map<String, Object> requestParams;
    private final HttpServletRequest servletRequest;

    private final Attributes attributes = new DefaultAttributes();

    public HttpRequest(String url, Map<String, Object> requestParams, HttpServletRequest servletRequest) {
        this.url = url;
        this.requestParams = requestParams;
        this.servletRequest = servletRequest;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getRemoteAddress() {
        return servletRequest.getRemoteAddr();
    }

    @Override
    public Client getClient() {
        return null;
    }

    @Override
    public Object getContent() {
        return requestParams;
    }

    @Nullable
    @Override
    public Session getSession(boolean create) {
        HttpSession httpSession = servletRequest.getSession(create);
        if (httpSession == null) {
            return null;
        }
        return (Session) httpSession.getAttribute(Session.class.getName());
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
