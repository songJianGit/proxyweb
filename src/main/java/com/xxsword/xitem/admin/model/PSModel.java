package com.xxsword.xitem.admin.model;

import lombok.Data;

import java.util.List;

@Data
public class PSModel {
    private List<String> comm;// 命令
    private String key;// 主键
    private String nodes;// 备注
}
