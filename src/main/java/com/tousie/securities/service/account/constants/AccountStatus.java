package com.tousie.securities.service.account.constants;

public enum AccountStatus {

    COMMON("0", "正常"),

    BAN("1", "封禁"),
    ;

    private final String code;
    private final String desc;

    AccountStatus(String code, String desc) {
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
