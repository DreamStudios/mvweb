package com.blueshit.neweast.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作工具类
 * @author 9527
 * @version 1.0
 * @email zhaohuaan0925@163.com
 * @created 2015/5/12 15:02
 * @description
 */
public class FileUtil {

    /**
     * 将文件对象持久化到硬盘
     * @param file 文件对象
     * @param fullPath 文件全路径
     */
    public static void transformToFile(MultipartFile file,String fullPath) throws IOException {
        transformToFile(file.getBytes(),fullPath);
    }

    /**
     * 将字节数组类型的文件持久化到硬盘
     * @param fileByte 需要持久化的字节数组
     * @param fullPath 文件全路径
     * @throws IOException 文件写入异常
     */
    public static void transformToFile(byte[] fileByte, String fullPath)
            throws IOException {
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
        stream.write(fileByte);
        stream.close();
    }

    /**
     * 创建目录
     * @param fullPath
     */
    public static void mkdirs(String fullPath) {
        File dirPath = new File(fullPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }
}
