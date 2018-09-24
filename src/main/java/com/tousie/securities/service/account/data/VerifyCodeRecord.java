package com.tousie.securities.service.account.data;

import javax.persistence.Table;

@Table
public class VerifyCodeRecord {

    private String serviceId;
    private String phone;
    private String code;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
