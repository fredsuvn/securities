package com.tousie.securities.common.status;

import javax.annotation.Nullable;

/**
 * @author sunqian
 */
public enum StatusEnum {
    OK(new Status("000000", null)),


    SERVICE_NOT_FOUND(new Status("900404", "服务不存在")),
    UNKNOWN_ERROR(new Status("900999", "未知错误")),
    INTERNAL_ERROR(new Status("900500", "内部错误"));

    private static StatusEnum[] values = values();

    @Nullable
    public static StatusEnum of(String code) {
        for (int i = 0; i < values.length; i++) {
            if (code.equals(values[i])) {
                return values[i];
            }
        }
        return null;
    }

    private final Status status;

    private final StatusEnum publicStatus;

    StatusEnum(Status status) {
        this.status = status;
        this.publicStatus = this;
    }

    StatusEnum(Status status, StatusEnum publicStatus) {
        this.status = status;
        this.publicStatus = publicStatus;
    }

    public String getCode() {
        return status.getCode();
    }

    @Nullable
    public String getDesc() {
        return status.getDesc();
    }

    public StatusEnum toPublic() {
        return publicStatus;
    }

    public Status toStatus() {
        return status;
    }
}
