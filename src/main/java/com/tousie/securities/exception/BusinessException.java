package com.tousie.securities.exception;

import com.tousie.securities.common.status.Status;
import com.tousie.securities.common.status.StatusEnum;

import javax.annotation.Nullable;

/**
 * @author sunqian
 */
public class BusinessException extends RuntimeException {

    private static String buildMessage(String code, String desc) {
        return "Code: " + code + ", Description: " + desc + "";
    }

    private final String code;

    @Nullable
    private final String desc;

    public BusinessException(String code, @Nullable String desc) {
        super(buildMessage(code, desc), null);
        this.code = code;
        this.desc = desc;
    }

    public BusinessException(String code, @Nullable String desc, @Nullable Throwable cause) {
        super(buildMessage(code, desc), cause);
        this.code = code;
        this.desc = desc;
    }

    public BusinessException() {
        this(StatusEnum.UNKNOWN_ERROR);
    }

    public BusinessException(StatusEnum statusEnum) {
        this(statusEnum, null);
    }

    public BusinessException(StatusEnum statusEnum, Throwable cause) {
        this(statusEnum.getCode(), statusEnum.getDesc(), cause);
    }

    public BusinessException(Status status) {
        this(status, null);
    }

    public BusinessException(Status status, Throwable cause) {
        this(status.getCode(), status.getDesc(), cause);
    }

    public String getCode() {
        return code;
    }

    @Nullable
    public String getDesc() {
        return desc;
    }

}
