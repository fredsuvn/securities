package com.tousie.securities.common.message;

import com.alibaba.fastjson.JSONObject;
import com.tousie.securities.common.id.IdService;
import com.tousie.securities.common.status.Status;
import com.tousie.securities.common.status.StatusEnum;
import com.tousie.securities.model.message.BiRequestMessage;
import com.tousie.securities.model.message.BiResponseMessage;
import com.tousie.securities.model.message.ResponseMessage;
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

    @Nullable
    public ResponseMessage toResponseMessage(@Nullable Object data) {
        if (data == null) {
            return toResponseMessage(StatusEnum.OK, null);
        }
        if (data instanceof Status) {
            return toResponseMessage(((Status) data).getCode(), ((Status) data).getDesc(), null);
        }
        return toResponseMessage(StatusEnum.OK, data);
    }

    public ResponseMessage toResponseMessage(StatusEnum statusEnum, @Nullable Object data) {
        return toResponseMessage(statusEnum.getCode(), statusEnum.getDesc(), data);
    }

    public ResponseMessage toResponseMessage(String code, @Nullable String desc, @Nullable Object data) {
        if (data instanceof ResponseMessage) {
            return (ResponseMessage) data;
        }
        return new ResponseMessage() {
            @Override
            public String getCode() {
                return code;
            }

            @Nullable
            @Override
            public String getDesc() {
                return desc;
            }

            @Nullable
            @Override
            public Object getData() {
                return data;
            }
        };
    }

    public BiRequestMessage toBiRequestMessage(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        return toBiRequestMessage(jsonObject.getString("id"), jsonObject.getString("url"), jsonObject.get("data"));
    }

    public BiRequestMessage toBiRequestMessage(@Nullable String id, @Nullable String url, @Nullable Object data) {
        return new BiRequestMessage() {
            @Nullable
            @Override
            public String getId() {
                return id;
            }

            @Nullable
            @Override
            public String getUrl() {
                return url;
            }

            @Nullable
            @Override
            public Object getData() {
                return data;
            }
        };
    }

    @Nullable
    public BiResponseMessage toBiResponseMessage(@Nullable String respondedId, @Nullable Object data) {
        if (data == null) {
            return toBiResponseMessage(respondedId, StatusEnum.OK, null);

        }
        if (data instanceof Status) {
            return toBiResponseMessage(respondedId, ((Status) data).getCode(), ((Status) data).getDesc(), null);
        }
        return toBiResponseMessage(respondedId, StatusEnum.OK, data);
    }

    public BiResponseMessage toBiResponseMessage(@Nullable String respondedId, StatusEnum statusEnum, @Nullable Object data) {
        return toBiResponseMessage(respondedId, statusEnum.getCode(), statusEnum.getDesc(), data);
    }

    public BiResponseMessage toBiResponseMessage(@Nullable String respondedId, String code, @Nullable String desc, @Nullable Object data) {
        return new BiResponseMessage() {
            @Nullable
            @Override
            public String getRespondedId() {
                return respondedId;
            }

            @Override
            public String getCode() {
                return code;
            }

            @Nullable
            @Override
            public String getDesc() {
                return desc;
            }

            @Nullable
            @Override
            public Object getData() {
                return data;
            }
        };
    }
}
