package com.xxsword.xitem.admin.domain.system.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 107L;

    private String loginName;

    private String password;


}
