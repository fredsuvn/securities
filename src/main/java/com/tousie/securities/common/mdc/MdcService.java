package com.tousie.securities.common.mdc;

import com.tousie.securities.port.RequestIdGenerator;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;

/**
 * @author sunqian
 */
@Component
public class MdcService {

    public static final String REQUEST_ID_KEY = "requestId";

    @Resource
    private RequestIdGenerator requestIdGenerator;

    public void putRequestId() {
        putRequestId(requestIdGenerator.generateId());
    }

    public void putRequestId(String requestId) {
        MDC.put(REQUEST_ID_KEY, requestId);
    }

    @Nullable
    public String getRequestId() {
        return MDC.get(REQUEST_ID_KEY);
    }

    public void removeRequestId() {
        MDC.remove(REQUEST_ID_KEY);
    }
}
