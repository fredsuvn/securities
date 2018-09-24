package com.tousie.securities.service.account.constants;

public enum AccountType {

    COMMON("0", "普通"),

    MANAGER("1", "经理"),

    BOT("2", "BOT"),
    ;

    private final String code;
    private final String desc;

    AccountType(String code, String desc) {
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
