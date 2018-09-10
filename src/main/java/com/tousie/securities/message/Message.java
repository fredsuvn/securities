package com.tousie.securities.message;

import javax.annotation.Nullable;

/**
 * @author sunqian
 */
public interface Message {

    String getId();

    @Nullable
    String getRespondedId();

    @Nullable
    String getStatus();

    @Nullable
    Object getData();

    enum Status {
        OK("0", "成功"),
        BUSINESS_ERROR("1", "业务错误"),
        INTERNAL_ERROR("9", "内部错误");

        private final String code;
        private final String desc;

        Status(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}
