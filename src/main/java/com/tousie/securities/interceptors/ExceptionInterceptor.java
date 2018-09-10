package com.tousie.securities.interceptors;

import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.ServiceCallExceptionInterceptor;
import com.sonluo.spongebob.spring.server.ServiceNotFoundException;
import com.tousie.securities.exception.BusinessException;
import com.tousie.securities.message.MessageService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author sunqian
 */
@Component
public class ExceptionInterceptor implements ServiceCallExceptionInterceptor {

    @Resource
    private MessageService messageService;

    @Override
    public Object doIntercept(Request request, Throwable throwable, Map<Object, Object> requestLocal) {
        if (throwable instanceof ServiceNotFoundException) {
            throw (ServiceNotFoundException) throwable;
        }
        if (throwable instanceof BusinessException) {
//            return messageService.buildMessage(request.get)
        }
        return null;
    }
}
