package com.tousie.securities.service.account.params.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author sunqian
 */
public class VerifyCodeRequest {

    @NotEmpty(message = "serviceId不能为空")
    private String serviceId;

    @NotEmpty(message = "电话不能为空")
    @Pattern(
            regexp = "\\d{11}",
            message = "电话格式如：12345678901")
    private String phone;

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
}
