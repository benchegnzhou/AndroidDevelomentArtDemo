package com.ztsc.commonutils.file;

import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.ztsc.commonutils.CommonUtil;
import com.ztsc.commonutils.logcat.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * .::::.
 * .::::::::.
 * :::::::::::  FUCK YOU
 * ..:::::::::::'
 * '::::::::::::'
 * .::::::::::
 * '::::::::::::::..
 * ..::::::::::::.
 * ``::::::::::::::::
 * ::::``:::::::::'        .:::.
 * ::::'   ':::::'       .::::::::.
 * .::::'      ::::     .:::::::'::::.
 * .:::'       :::::  .:::::::::' ':::::.
 * .::'        :::::.:::::::::'      ':::::.
 * .::'         ::::::::::::::'         ``::::.
 * ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 * '.:::::'                    ':'````..
 */


/**
 * Created by lenovo on 2016/10/10.
 * 本地文件操作的工具类
 */
public class FileUtils2 {
    /**
     * Environment.getExternalStorageDirectory()getRootDirectory()//获取手机根目录
     * Environment.getExternalStorageDirectory()getExternalStorageDirectory()//获取SD卡根目录
     */
//    public static String sAbsolutePath = Environment.getExternalStorageDirectory().getPath();  //获取内置存储卡的路径
//    public static String sCompanyRootDir = sAbsolutePath + "";         //创建公司文件目录
    public static String sAppDir = "/ztsc/lifePublic";           //创建应用层目录

    //---------------------可是使用的目录---------------------------
    public static String APK_DOWN_DIR = sAppDir + "/down/apk";                  //app更新包
    public static String IMAGE_DOWN_DIR = sAppDir + "/down/image";              //下载或缓存的图片
    public static String IMAGE_SAVE_DIR = sAppDir + "/qrsave/image";             //下载或缓存的图片
    public static String DATA_PRIMARY_DIR = sAppDir + "/data";                   //基础数据
    public static String APP_LOG_DIR = sAppDir + "/log";                         //应用日志


    /**
     * 本地下载更新文件的目录
     * 优先使用sd卡的存储路径，当sd卡未就绪的情况下使用手机内存空间
     * 但是实际测试的时候sd卡路径在没有sd 的时候使用的是手机内存虚拟出来的内存
     *
     * @return
     */
    public static String getApkUpdataPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath() + APK_DOWN_DIR;
            createFileDir(path);
            return path;
        } else {
            String path = CommonUtil.getInstance().getApplicationContext().getCacheDir().getAbsolutePath() + APK_DOWN_DIR;
            createFileDir(path);
            return path;
        }
    }


    /**
     * 本地下载更新头像等信息文件的目录
     * 优先使用sd卡的存储路径，当sd卡未就绪的情况下使用手机内存空间
     * 但是实际测试的时候sd卡路径在没有sd 的时候使用的是手机内存虚拟出来的内存
     *
     * @return
     */
    public static String getImageDownPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath() + IMAGE_DOWN_DIR;
            createFileDir(path);
            return path;
        } else {
            String path = CommonUtil.getInstance().getApplicationContext().getCacheDir().getAbsolutePath() + IMAGE_DOWN_DIR;
            createFileDir(path);
            return path;
        }
    }

    /**
     *
     * 优先使用sd卡的存储路径，当sd卡未就绪的情况下使用手机内存空间
     * 但是实际测试的时候sd卡路径在没有sd 的时候使用的是手机内存虚拟出来的内存
     *
     * @return
     */
    public static String getImageSavePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath() + IMAGE_SAVE_DIR;
            createFileDir(path);
            return path;
        } else {
            String path = CommonUtil.getInstance().getApplicationContext().getCacheDir().getAbsolutePath() + IMAGE_SAVE_DIR;
            createFileDir(path);
            return path;
        }
    }

    /**
     * 基础数据，不可以被用户清除
     * 优先使用sd卡的存储路径，当sd卡未就绪的情况下使用手机内存空间
     * 但是实际测试的时候sd卡路径在没有sd 的时候使用的是手机内存虚拟出来的内存
     *
     * @return
     */
    public static String getPrimaryDirPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath() + DATA_PRIMARY_DIR;
            createFileDir(path);
            return path;
        } else {
            String path = CommonUtil.getInstance().getApplicationContext().getCacheDir().getAbsolutePath() + DATA_PRIMARY_DIR;
            createFileDir(path);
            return path;
        }
    }








    /**
     * 创建文件指定文件夹
     *
     * @param path
     */
    private static void createFileDir(String path) {
        File PathDir = new File(path);
        if (!PathDir.exists()) {
            PathDir.mkdirs();
        }
    }




    /***
     * 将大文件拷贝到sd卡目录
     * 待测试
     *
     * @param targetFilePath 文件拷贝到目标路径
     * @param assetFileName  需要拷贝的文件
     * @throws IOException
     */
    public static void copyAssetsDataToSD(String assetFileName, String targetFilePath) throws IOException {
        //目标路径或者需要拷贝的文件不存在，将会退出
        if (TextUtils.isEmpty(assetFileName) || TextUtils.isEmpty(targetFilePath)) {
            return;
        }
        File file = new File(targetFilePath);
        if (file.isFile()) {   //是一个文件夹，则删除
            file.delete();
        } else if (file.exists()) {
            return;     //文件已经存在，不在执行拷贝
        }

        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(targetFilePath);

        myInput =CommonUtil.getInstance().getApplicationContext().getAssets().open(assetFileName);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();
    }


    /**
     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     *
     * @param filePath
     * @param conent
     */
    public static void textFileAppendWrite(String filePath, String conent) {
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            file.delete();   //如果是一个空文件家，直接执行删除操作
        }

        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 覆盖的方式写入文本数据：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     *
     * @param file
     * @param conent
     */
    public static void textFileWrite(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, false)));

            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static String TAG = "FILE_UTIL";

    public static byte[] readFromFile(String fileName, int offset, int len) {
        if (fileName == null) {
            return null;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            Log.i(TAG, "readFromFile: file not found");
            return null;
        }

        if (len == -1) {
            len = (int) file.length();
        }

        Log.d(TAG, "readFromFile : offset = " + offset + " len = " + len + " offset + len = " + (offset + len));

        if (offset < 0) {
            Log.e(TAG, "readFromFile invalid offset:" + offset);
            return null;
        }
        if (len <= 0) {
            Log.e(TAG, "readFromFile invalid len:" + len);
            return null;
        }
        if (offset + len > (int) file.length()) {
            Log.e(TAG, "readFromFile invalid file len:" + file.length());
            return null;
        }

        byte[] b = null;
        try {
            RandomAccessFile in = new RandomAccessFile(fileName, "r");
            b = new byte[len]; // 创建合适文件大小的数组
            in.seek(offset);
            in.readFully(b);
            in.close();

        } catch (Exception e) {
            Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 读取文本文件中的内容，用行的方式读取
     *
     * @param strFilePath
     * @return
     */
    public static String ReadTextFile(String strFilePath) {
        String path = strFilePath;
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            LogUtil.e("TestFile", "The File doesn't not exist.");
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while ((line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }
                    instream.close();
                }
            } catch (java.io.FileNotFoundException e) {
                LogUtil.e("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                LogUtil.e("TestFile", e.getMessage());
            }
        }
        return content;
    }

    /**
     * 读取文本文件中的内容，用行的方式读取
     * 小区的搜索历史解析获取
     *
     * @param strFilePath
     * @return
     */
//    public static List<CommunityVillageHistoryJsonBean.CommunitiesBean> VillageTextFileRead(String strFilePath) {
//        String path = strFilePath;
//        List<CommunityVillageHistoryJsonBean.CommunitiesBean> communitiesBeenList = new ArrayList<>();
//        //打开文件
//        File file = new File(path);
//        //如果path是传递过来的参数，可以做一个非目录的判断
//        if (file.isDirectory()) {
//            file.delete();   //被文件夹占用直接删除
//            LogUtil.e("TestFile", "The File doesn't not exist.");
//        } else {
//            try {
//                InputStream instream = new FileInputStream(file);
//                if (instream != null) {
//                    InputStreamReader inputreader = new InputStreamReader(instream);
//                    BufferedReader buffreader = new BufferedReader(inputreader);
//                    String line;
//                    //分行读取
//                    while ((line = buffreader.readLine()) != null) {
//                        String[] split = line.split("[,]");
//                        if (split != null && split.length > 0) {
//                            CommunityVillageHistoryJsonBean.CommunitiesBean communitiesBean = new CommunityVillageHistoryJsonBean.CommunitiesBean();
//                            communitiesBean.setZoneId(split[0]);
//                            communitiesBean.setZoneName(split[1]);
//                            if (!TextUtils.isEmpty(split[2].trim())) {
//                                communitiesBean.setZoneX(split[2]);
//                            } else {
//                                communitiesBean.setZoneX("");    //默认返回""
//                            }
//                            if (!TextUtils.isEmpty(split[3].trim())) {
//                                communitiesBean.setZoneY(split[3]);
//                            } else {
//                                communitiesBean.setZoneY("");
//                            }
//                            communitiesBeenList.add(communitiesBean);
//                        }
//
//                    }
//                    instream.close();
//                }
//            } catch (java.io.FileNotFoundException e) {
//                LogUtil.e("TestFile", "The File doesn't not exist.");
//            } catch (Exception e) {
//                LogUtil.e("TestFile", e.getMessage());
//                file.delete();   //被文件夹占用直接删除
//            }
//        }
//        return communitiesBeenList;
//    }


    /**
     * 读取文本文件中的内容，用行的方式读取
     * 小区的搜索历史解析获取
     *
     * @param strFilePath
     * @return
     */
//    public static List<VisitorHistoryJsonBean.VisitorBean> VisitorHistoryFileRead(String strFilePath) {
//        String path = strFilePath;
//        List<VisitorHistoryJsonBean.VisitorBean> communitiesBeenList = new ArrayList<>();
//        //打开文件
//        File file = new File(path);
//        //如果path是传递过来的参数，可以做一个非目录的判断
//        if (file.isDirectory()) {
//            file.delete();   //被文件夹占用直接删除
//            LogUtil.e("TestFile", "The File doesn't not exist.");
//        } else {
//            try {
//                InputStream instream = new FileInputStream(file);
//                if (instream != null) {
//                    InputStreamReader inputreader = new InputStreamReader(instream);
//                    BufferedReader buffreader = new BufferedReader(inputreader);
//                    String line;
//                    //分行读取
//                    while ((line = buffreader.readLine()) != null) {
//                        String[] split = line.split("[,]");
//                        if (split != null && split.length > 0) {
//                            VisitorHistoryJsonBean.VisitorBean visitorBean = new VisitorHistoryJsonBean.VisitorBean();
//                            visitorBean.setVisitorName(split[0]);
//                            visitorBean.setIdCard(split[1]);
//                            visitorBean.setSaveTime(Long.parseLong(split[2]));
//
//                            communitiesBeenList.add(visitorBean);
//                        }
//
//                    }
//                    instream.close();
//                }
//            } catch (java.io.FileNotFoundException e) {
//                LogUtil.e("TestFile", "The File doesn't not exist.");
//            } catch (Exception e) {
//                LogUtil.e("TestFile", e.getMessage());
//                file.delete();   //被文件夹占用直接删除
//            }
//        }
//        return communitiesBeenList;
//    }


    /**
     * 解析本地文件，将对应的城市解析成对象
     *
     * @param strFilePath
     * @return
     */
//    public static List<CityDictionary> cityDictionarieFileRead(String strFilePath) {
//        String path = strFilePath;
//        List<CityDictionary> communitiesBeenList = new ArrayList<>();
//        //打开文件
//        File file = new File(path);
//        //如果path是传递过来的参数，可以做一个非目录的判断
//        if (file.isDirectory()) {
//            file.delete();   //被文件夹占用直接删除
//            LogUtil.e("TestFile", "The File doesn't not exist.");
//        } else {
//            try {
//                InputStream instream = new FileInputStream(file);
//                if (instream != null) {
//                    InputStreamReader inputreader = new InputStreamReader(instream);
//                    BufferedReader buffreader = new BufferedReader(inputreader);
//                    String line;
//                    //分行读取
//                    while ((line = buffreader.readLine()) != null) {
//                        String[] split = line.split("[,]");
//                        if (split != null && split.length > 0) {
//                            CityDictionary cityDictionary = new CityDictionary();
//
//                            cityDictionary.setCityName(split[0]);
//                            cityDictionary.setCityENAME(split[1]);
//                            cityDictionary.setCityCODE(split[2]);
//                            cityDictionary.setCityADDRESS(split[3]);
//                            cityDictionary.setCityX(split[4]);
//                            cityDictionary.setCityY(split[5]);
//                            communitiesBeenList.add(cityDictionary);
//                        }
//                    }
//                    inputreader.close();
//                }
//                instream.close();
//
//            } catch (java.io.FileNotFoundException e) {
//                LogUtil.e("The File doesn't not exist.", e);
//            } catch (Exception e) {
//                LogUtil.e("城市文件读取异常了：", e.getMessage());
//                file.delete();   //被文件夹占用直接删除
//            }
//        }
//        if (communitiesBeenList.size() > 0) {
//            communitiesBeenList.remove(0);    //移除首条信息
//        }
//        return communitiesBeenList;
//    }


    //--------------------------------------  文件大小处理及单位格式转换  -----------------------------------------//

    /**
     * 获取文件夹大小
     *
     * @param file 需要遍历的根目录
     * @return
     */
    public static long getFolderSize(java.io.File file) {

        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

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
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return 6
     */
    public void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     * 文件单位格式化
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";

        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";

        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";

        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";

        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";

    }


    //--------------------------------------------  使用特定字符或字符串将字符串切割，返回list对象  -----------------------------------------------------//

    /**
     * 将一个字符串以符号 character 的形式分割
     *
     * @param contextText 原始字符串
     * @param character   用月切割匹配的字符串或字符
     * @return
     */
    public static List<String> String2List2(String contextText, String character) {
        List<String> arrayList = new ArrayList<>();
        if (TextUtils.isEmpty(contextText)) {
            return arrayList;
        }
        if (contextText.endsWith(character)) {  //判断是不是以切割符号结尾的
            contextText = contextText.substring(0, contextText.lastIndexOf(character));
        }
        if (contextText.contains(character)) {  //如果没有找到指定的切割符号，直接将这个字符串返回
            String[] split = contextText.split(character);
            arrayList = Arrays.asList(split);
        } else {
            arrayList.add(contextText);
        }
        return arrayList;
    }


    /**
     * 将一个字符串以符号 character 的形式分割
     *
     * @param contextText 原始字符串
     * @param character   用月切割匹配的字符串或字符
     * @return
     */
    public static List<String> String2List(String contextText, String character) {
        List<String> arrayList = new ArrayList<>();
        if (TextUtils.isEmpty(contextText)) {
            return arrayList;
        }
        if (contextText.endsWith(character)) {  //判断是不是以切割符号结尾的
            contextText = contextText.substring(0, contextText.lastIndexOf(character));
        }
        if (contextText.contains(character)) {  //如果没有找到指定的切割符号，直接将这个字符串返回
            String[] split = contextText.split(character);
            arrayList = Arrays.asList(split);
        } else {
            arrayList.add(contextText);
        }
        return arrayList;
    }


    /**
     * 文件的存储形式必须是  key=value 的形式
     * <p>
     * 给定文件路径和主键值获取对应的元素
     * 此方法接收一个key，在配置文件中获取相应的value
     *
     * @param key
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getValueFromFile(String key, String filePath) throws Exception {
        Properties pro = new Properties();//获取配置文件的对象
        //FileReader in = new FileReader("pro.txt");//获取输入流
        FileReader in = new FileReader(filePath);//获取输入流
//            AssetManager assetManager = MApplication.sAppContext.getAssets();
//            InputStreamReader in = new InputStreamReader(assetManager.open("pro.txt"));

        pro.load(in);//将流加载到配置文件对象中
        in.close();
        return pro.getProperty(key);//返回根据key获取的value值

    }

    /**
     * 给定文件名称和主键值获取对应的元素，注意该文件必须存放在资产目录
     * 此方法接收一个key，在配置文件中获取相应的value
     *
     * @param key
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String getValueFromAssert(String key, String fileName) throws Exception {
        Properties pro = new Properties();//获取配置文件的对象
        AssetManager assetManager = CommonUtil.getInstance().getApplicationContext().getAssets();
//        InputStreamReader in = new InputStreamReader(assetManager.open("pro.txt"));
        InputStreamReader in = new InputStreamReader(assetManager.open(fileName));

        pro.load(in);//将流加载到配置文件对象中
        in.close();
        return pro.getProperty(key);//返回根据key获取的value值

    }

    /**
     * 文件的存储形式必须是  key=value 的形式
     * <p>
     * 给定文件路径和主键值获取对应的元素
     * 此方法接收一个key，在配置文件中获取相应的value
     *
     * @param key
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getValue(String key, String filePath) throws Exception {
        Properties pro = new Properties();//获取配置文件的对象
        //FileReader in = new FileReader("pro.txt");//获取输入流
        FileReader in = new FileReader(filePath);//获取输入流
//            AssetManager assetManager = MApplication.sAppContext.getAssets();
//            InputStreamReader in = new InputStreamReader(assetManager.open("pro.txt"));

        pro.load(in);//将流加载到配置文件对象中
        in.close();
        return pro.getProperty(key);//返回根据key获取的value值

    }

}
