package com.xxsword.xitem.admin.model.proxy;

import lombok.Data;

@Data
public class JSONDBComm {
    private String cdate;// 创建时间
    private String ldate;// 更新时间
    private String comm;// 命令
    private String notes;// 备注
}
