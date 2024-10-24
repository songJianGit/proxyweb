package com.xxsword.xitem;

import com.xxsword.xitem.admin.utils.ProxyUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XitemApplication {

    public static void main(String[] args) {
        SpringApplication.run(XitemApplication.class, args);
        ProxyUtils.initComm();
    }

}
