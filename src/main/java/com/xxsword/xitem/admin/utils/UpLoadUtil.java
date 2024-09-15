package com.xxsword.xitem.admin.utils;

import com.xxsword.xitem.admin.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 上传工具类
 *
 * @author songJian
 * @version 2018-3-29
 */
@Slf4j
public class UpLoadUtil {

    public static final String PATH_INFO = "/fileinfo";// 所有文件路径都会加的前缀，用作nginx转发可以减轻java压力

    private static final String PATH_DEF = "/def";// 默认文件夹

    private static final String PATH_TEMP = "/temp";// 临时文件夹

    public static String upload(MultipartFile file) {
        return upload(file, PATH_DEF);
    }

    public static String upload(MultipartFile file, String path) {
        path = doPath(path);// 格式检查
        path = path + getTIMEPath();
        return uploadLocal(file, path);
    }

    /**
     * 获取项目路径
     *
     * @return
     */
    public static String getProjectPath() {
        String p = getPath();
        return p.substring(0, p.length() - PATH_INFO.length());
    }

    /**
     * 校验目录路径的格式（要前带杠杠，后不带）
     *
     * @param path
     * @return
     */
    public static String doPath(String path) {
        path = replaceSeparator(path);
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * 将文件从临时文件夹里面移动出来（本方法专门用作临时文件夹的文件移动，不要用做其它）
     */
    public static String tempToFileInfoPath(String url) {
        String[] p0 = FilenameUtils.getFullPathNoEndSeparator(url).split(PATH_TEMP);
        String newFileName = Utils.getuuid() + "." + FilenameUtils.getExtension(url);
        String newPath = PATH_DEF + getTIMEPath() + "/";
        try {
            FileUtils.moveFile(new File(url), new File(p0[0] + newPath + newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PATH_INFO + newPath + newFileName;
    }

    /**
     * 获取时间分隔字符
     */
    public static String getTIMEPath() {
        return "/" + DateUtil.now(DateUtil.sdfA4);
    }

    /**
     * @param file
     * @param path
     * @return
     */
    private static String uploadLocal(MultipartFile file, String path) {
        if (file != null && !file.isEmpty()) {
            try {
                String fileSuffix = "." + FilenameUtils.getExtension(file.getOriginalFilename());
                String id = Utils.getuuid();
                String rootPath = getPath();
                String filePath = path + "/" + id + fileSuffix;
                Utils.hasFolder(rootPath + path);
                file.transferTo(new File(rootPath, filePath));
                return PATH_INFO + filePath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 不指定资源目录时，默认路径的获取
     *
     * @return
     */
    private static String getDPath() {
        try {
            String url = ResourceUtils.getURL("").getPath();
            url = replaceSeparator(url);
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);// 去掉最后一个杠，保持：前带杠杠，后不带的风格
            }
            String pathStr = url + PATH_INFO;
            Utils.hasFolder(pathStr);
            log.debug("uploadPath:" + pathStr);
            return replaceSeparator(pathStr);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定的资源路径
     *
     * @return
     */
    private static String getPath() {
        int type = Integer.parseInt(SystemConfig.getProperty("filepath.type"));
        if (type == 1) {
            return getDPath();
        } else if (type == 2) {
            String path = SystemConfig.getProperty("filepath.path") + PATH_INFO;
            Utils.hasFolder(path);
            log.debug("uploadPath:" + path);
            return replaceSeparator(path);
        }
        return getDPath();
    }


    /**
     * 将所有反斜杠换成正斜杠
     *
     * @param path
     * @return
     */
    private static String replaceSeparator(String path) {
        return path.replaceAll("\\\\", "/");
    }

}
