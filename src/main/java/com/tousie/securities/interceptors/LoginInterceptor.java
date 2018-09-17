package com.tousie.securities.interceptors;

import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.ServiceCallInterceptor;
import com.sonluo.spongebob.spring.server.Session;
import com.tousie.securities.common.status.StatusEnum;
import com.tousie.securities.exception.BusinessException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author sunqian
 */
@Component
public class LoginInterceptor implements ServiceCallInterceptor {

    @Override
    public int getOrder() {
        return  Integer.MIN_VALUE + 100;
    }

    @Override
    public void doIntercept(Request request, Object[] args, @Nullable Object result, Map<Object, Object> requestLocal) {
        Session session = request.getSession(true);
//        if (session == null || !session.isOpen()) {
            throw new BusinessException(StatusEnum.NEED_LOGIN);
//        }
    }
}
