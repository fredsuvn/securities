package com.tousie.securities.service.account.params.request;

/**
 * @author sunqian
 */
public class VerifyPhoneRequest {

    private String phone;
    private String verificationCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
