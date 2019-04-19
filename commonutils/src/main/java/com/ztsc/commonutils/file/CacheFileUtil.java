package com.ztsc.commonutils.file;

import com.ztsc.commonutils.logcat.LogUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by benchengzhou on 2018/11/20  16:07 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 缓存管理类
 * 类    名： CacheFileUtil
 * 备    注：
 */

public class CacheFileUtil {
    private CacheFileUtil() {
    }


    /**
     * 获取缓存文件的大小
     * 由于缓存可能存在于多个文件当中所以这里要求传入一个集合的形式
     * 我们将会统计这个集合文件的大小一并输出,输出结果是一个long型的
     *
     * @param cacheDirList
     */
    public static long getCacheFileSizeWithoutFormet(ArrayList<String> cacheDirList) {
        ArrayList<File> cacheFileList = new ArrayList<>();
        for (int i = 0; i < cacheDirList.size(); i++) {
            File file = new File(cacheDirList.get(i));
            if (file.exists()) {
                cacheFileList.add(file);
            }
        }
        return getSizeUsed(cacheFileList);
    }

    /**
     * 获取缓存文件的大小
     * 由于缓存可能存在于多个文件当中所以这里要求传入一个集合的形式
     * 我们将会统计这个集合文件的大小一并输出,带有格式化后的大小
     *
     * @param cacheDirList
     */
    public static String getCacheFileSizeWithFormet(ArrayList<String> cacheDirList) {
        return FileSizeUtil.FormetFileSize(getCacheFileSizeWithoutFormet(cacheDirList));
    }


    /**
     * 读取文件存储空间的大小
     */
    public static long getSizeUsed(ArrayList<File> cacheFileList) {
        long folderSize = 0;
        for (int i = 0; i < cacheFileList.size(); i++) {
            folderSize += getFolderSize(cacheFileList.get(i));
        }
        return folderSize;
    }


    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                    LogUtil.e("----当前统计到的文件-----" + fileList[i].getAbsolutePath());
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return size/1048576;
        return size;
    }


    /**
     * 删除文件夹和下面所有的文件
     */
    public static void delAll(ArrayList<String> cacheDirList) {

        for (int i = 0; i < cacheDirList.size(); i++) {
            File file = new File(cacheDirList.get(i));
            if (file.exists() && file.isDirectory()) {
                delFolder(file.getAbsolutePath());
            } else if (file.exists() && file.isFile()) {
                delAllFile(file.getAbsolutePath());
            }
        }
    }



    /**
     * 删除文件夹
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除指定文件夹下所有文件
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
}

