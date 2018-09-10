package com.tousie.securities.message;

import com.tousie.securities.common.id.IdService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;

/**
 * @author sunqian
 */
@Component
public class MessageService {

    @Resource
    private IdService idService;

    public Message buildMessage(@Nullable String requestId, @Nullable String responseStatus, @Nullable Object data) {
        return new Message() {
            @Override
            public String getId() {
                return getRespondedId();
            }

            @Nullable
            @Override
            public String getRespondedId() {
                return requestId;
            }

            @Nullable
            @Override
            public String getStatus() {
                return responseStatus;
            }

            @Nullable
            @Override
            public Object getData() {
                return data;
            }
        };
    }

    private String generateRespondedId() {
        return idService.next();
    }
}
