package com.tousie.securities.service.account.constants;

public enum RecordChangeType {

    IN("0", "收入"),

    OUT("1", "支出"),
    ;

    private final String code;
    private final String desc;

    RecordChangeType(String code, String desc) {
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
