package com.tousie.securities.interceptors;

import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.ServiceCallInterceptor;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author sunqian
 */
public class LoginInterceptor implements ServiceCallInterceptor {

    @Override
    public int getOrder() {
        return  Integer.MIN_VALUE + 100;
    }

    @Override
    public void doIntercept(Request request, @Nullable Object result, Map<Object, Object> requestLocal) {

    }
}
