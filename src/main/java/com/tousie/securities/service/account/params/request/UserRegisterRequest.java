package com.tousie.securities.service.account.params.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author sunqian
 */
public class UserRegisterRequest {

    @NotEmpty(message = "昵称不能为空")
    @Pattern(regexp = "(\\w|\\d|_){6,32}", message = "昵称只能用数字，字母和下划线,6到32位")
    private String nickName;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "电话不能为空")
    @Pattern(
            regexp = "\\d{11}",
            message = "电话格式如：12345678901")
    private String phone;

    @NotEmpty(message = "验证码不能为空")
    @Pattern(
            regexp = "\\d{6}",
            message = "验证码为6位数字")
    private String verifyCode;

    private String parentId;
    private String inviteCode;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

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

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
