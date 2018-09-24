package com.tousie.securities.service.account.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountModel {

    private String id;
    private String password;
    private String nickName;
    private String phone;
    private String status;
    private String type;
    private Date createDate;
    private AccountModel parent;
    private AccountModel referee;
    private AccountModel manager;
    private Long amt;
    private List<AccountModel> subAccount = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public AccountModel getParent() {
        return parent;
    }

    public void setParent(AccountModel parent) {
        this.parent = parent;
    }

    public AccountModel getReferee() {
        return referee;
    }

    public void setReferee(AccountModel referee) {
        this.referee = referee;
    }

    public AccountModel getManager() {
        return manager;
    }

    public void setManager(AccountModel manager) {
        this.manager = manager;
    }

    public Long getAmt() {
        return amt;
    }

    public void setAmt(Long amt) {
        this.amt = amt;
    }

    public List<AccountModel> getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(List<AccountModel> subAccount) {
        this.subAccount = subAccount;
    }
}
