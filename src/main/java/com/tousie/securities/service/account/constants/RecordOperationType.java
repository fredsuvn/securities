package com.tousie.securities.service.account.constants;

public enum RecordOperationType {

    REGISTER("0", "注册赠送"),

    SYSTEM("1", "系统操作"),

    MANAGER("2", "经理操作"),

    TRADE("3", "交易操作"),
    ;

    private final String code;
    private final String desc;

    RecordOperationType(String code, String desc) {
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
