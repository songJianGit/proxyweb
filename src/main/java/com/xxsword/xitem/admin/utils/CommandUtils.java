package com.xxsword.xitem.admin.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Slf4j
public class CommandUtils {
    /**
     * 执行命令
     *
     * @param command
     * @return
     */
    public static String comm(String command) {
        if (StringUtils.isBlank(command)) {
            return null;
        }
        try {
            // 使用Runtime来执行命令
//            Process process = Runtime.getRuntime().exec(command);
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});

            // 创建一个新的输入流来读取命令的结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // 逐行读取输出
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
//                log.info(line);
                stringBuilder.append(line);
            }

            // 关闭流
            reader.close();

            // 等待进程结束，并获取退出码
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                log.info("Command executed successfully.");
            } else {
                log.warn("Command execution failed with exit code: " + exitVal);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
