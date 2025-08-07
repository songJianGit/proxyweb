package com.xxsword.xitem.admin.model.proxy;

import lombok.Data;

/**
 * 代理命令基础对象
 */
@Data
public class ProxyBase {
    private String key;// 主键
    private String notes;// 备注
    private Integer initStart;// 是否在java启动时，一起启动（若已启动则不管） 1-是 0-否
}
