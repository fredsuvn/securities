package com.tousie.securities.common.status;

import javax.annotation.Nullable;

/**
 * @author sunqian
 */
public class Status {

    private final String code;

    @Nullable
    private final String desc;

    Status(String code, @Nullable String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    @Nullable
    public String getDesc() {
        return desc;
    }
}
