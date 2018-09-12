package com.tousie.securities.port.http.session;

//import com.sonluo.spongebob.spring.server.Session;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * @author sunqian
 */
@WebListener
@Component
public class HttpSessionAttributeListener implements javax.servlet.http.HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
//        ValidHttpSession session = (ValidHttpSession) se.getSession().getAttribute(Session.class.getName());
//        session.setAttribute(se.getName(), se.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
//        ValidHttpSession session = (ValidHttpSession) se.getSession().getAttribute(Session.class.getName());
//        session.removeAttribute(se.getName());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
//        attributeAdded(se);
    }
}
