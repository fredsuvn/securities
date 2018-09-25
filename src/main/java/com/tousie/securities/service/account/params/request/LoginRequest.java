package com.tousie.securities.service.account.params.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author sunqian
 */
public class LoginRequest {

    @NotEmpty(message = "电话不能为空")
    @Pattern(
            regexp = "\\d{11}",
            message = "电话格式如：12345678901")
    private String phone;

    @NotEmpty(message = "密码不能为空")
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
