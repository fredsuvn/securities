package com.tousie.securities.common.verifycode;

public class VerifyCode {

    private final String serviceId;
    private final String phone;
    private final String code;
    private final long lastTime = System.currentTimeMillis();

    public VerifyCode(String serviceId, String phone, String code) {
        this.serviceId = serviceId;
        this.phone = phone;
        this.code = code;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getPhone() {
        return phone;
    }

    public String getCode() {
        return code;
    }

    public long getLastTime() {
        return lastTime;
    }
}
