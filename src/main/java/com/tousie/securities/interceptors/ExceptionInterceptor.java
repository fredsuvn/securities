package com.tousie.securities.interceptors;

import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.ServiceCallExceptionInterceptor;
import com.sonluo.spongebob.spring.server.ServiceNotFoundException;
import com.tousie.securities.common.status.StatusEnum;
import com.tousie.securities.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sunqian
 */
@Component
public class ExceptionInterceptor implements ServiceCallExceptionInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class);

    @Override
    public Object doIntercept(Request request, Throwable throwable, Map<Object, Object> requestLocal) {
        if (throwable instanceof ServiceNotFoundException) {
            return StatusEnum.SERVICE_NOT_FOUND.toStatus();
        }
        if (throwable instanceof BusinessException) {
            logger.info("Business exception: {}", throwable.getMessage());
            StatusEnum statusEnum = StatusEnum.of(((BusinessException) throwable).getCode());
            if (statusEnum == null) {
                return StatusEnum.UNKNOWN_ERROR.toStatus();
            }
            statusEnum = statusEnum.toPublic();
            return statusEnum.toPublic().toStatus();
        }
        if (throwable instanceof Exception) {
            logger.error("Exception occurs!", throwable);
        }
        return StatusEnum.INTERNAL_ERROR.toStatus();
    }
}
