package com.xxsword.xitem.admin.model.user;

import lombok.Data;

@Data
public class User {
    private String loginName;
    private String password;

    public User(String loginName, String password) {
        this.loginName = loginName;
        this.password = password;
    }
}
