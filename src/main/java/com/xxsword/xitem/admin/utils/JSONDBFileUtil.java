package com.xxsword.xitem.admin.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class JSONDBFileUtil {

    /**
     * 获取配置信息
     *
     * @return
     */
    public static JSONObject getConf() {
        return getJSONObjectByPath(UpLoadUtil.getProjectPath() + UpLoadUtil.PATH_INFO + "/proxyweb.json");
    }

    public static JSONObject getConfGoProxy() {
        return getConf().getJSONObject("go_proxy");
    }

    public static JSONArray getConfUser() {
        return getConf().getJSONArray("user");
    }

    public static void addJSONObjectToFile(String filePath, String key, JSONObject jsonObject) {
        JSONObject jsonObjectAll = getJSONObjectByPath(filePath);
        if (jsonObjectAll == null) {
            jsonObjectAll = new JSONObject();
        }
        jsonObjectAll.put(key, jsonObject);
        setJSONObjectToFile(filePath, jsonObjectAll);
    }

    public static void delJSONObjectToFile(String filePath, String key) {
        JSONObject jsonObject = getJSONObjectByPath(filePath);
        if (jsonObject != null) {
            log.info("delDB key:{}", key);
            jsonObject.remove(key);
            setJSONObjectToFile(filePath, jsonObject);
        }
    }

    public static JSONObject getJSONObjectAllByPath(String filePath) {
        return getJSONObjectByPath(filePath);
    }

    /**
     * 获取整个文件
     *
     * @param filePath
     * @return
     */
    private static JSONObject getJSONObjectByPath(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // 读取文件内容到字符串中
            StringBuilder contentBuilder = new StringBuilder();
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                contentBuilder.append(currentLine);
            }
            String content = contentBuilder.toString();
            return JSON.parseObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 覆盖整个文件
     *
     * @param filePath
     * @param jsonObject
     */
    private static void setJSONObjectToFile(String filePath, JSONObject jsonObject) {
        Path path = Paths.get(filePath); // 文件路径
        // 检查文件是否存在，如果不存在则创建文件
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            String jsonStr = jsonObject.toJSONString();
            // 写入JSON字符串
            Files.write(path, jsonStr.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
