package com.tousie.securities.common.status;

import javax.annotation.Nullable;

/**
 * @author sunqian
 */
public enum StatusEnum {
    OK(new Status("000000", null)),

    SERVICE_NOT_FOUND(new Status("900404", "服务不存在")),
    UNKNOWN_ERROR(new Status("900999", "未知错误")),
    INTERNAL_ERROR(new Status("900500", "内部错误")),
    PARAM_ERROR(new Status("910000", "参数错误")),


    //Account with 10xxxx
    ACCOUNT_NOT_FOUND(new Status("100000", "账户未找到")),

    //Login with 11xxxx
    NEED_LOGIN(new Status("110003", "需要登录")),
    LOGIN_IN_WRONG(new Status("110000", "账户不存在或密码错误")),
    LOGIN_IN_WRONG_ACCOUNT(new Status("110001", "账户不存在"), LOGIN_IN_WRONG),
    LOGIN_IN_WRONG_PASSWORD(new Status("110002", "账户密码错误"), LOGIN_IN_WRONG),

    //register 12xxxx
    REGISTER_PARAM_ERROR(new Status("120000", "注册参数错误")),
    INVITE_CODE_ERROR(new Status("120001", "邀请码错误")),
    VERIFY_CODE_ERROR(new Status("120002", "验证码错误或者超时")),


    ;

    private static StatusEnum[] values = values();

    @Nullable
    public static StatusEnum of(String code) {
        for (int i = 0; i < values.length; i++) {
            if (code.equals(values[i].getCode())) {
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
