package com.tousie.securities.service.account.params.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author sunqian
 */
public class UserRegisterRequest {

    @NotEmpty(message = "用户名不能为空")
    @Pattern(regexp = "\\w{6,32}", message = "用户名只能用数字，字母和下划线,6到32位")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    private String password;

    //    @NotEmpty(message = "用户名不能为空")
//    @Pattern(
//            regexp = "(\\w\\.)+@(\\w\\.?)+(\\w)",
//            message = "用户名只能用数字，字母和下划线,6到32位")
//    private String mail;

    @NotEmpty(message = "电话不能为空")
    @Pattern(
            regexp = "(\\+\\d{2})-\\d+",
            message = "电话格式如：+86-12345678901")
    private String phone;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
