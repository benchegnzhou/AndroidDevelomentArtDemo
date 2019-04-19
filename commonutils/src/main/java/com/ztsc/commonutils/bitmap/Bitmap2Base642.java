package com.ztsc.commonutils.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Base64;
import com.ztsc.commonutils.logcat.LogUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by benchengzhou on 2017/3/28 11:13 .
 * 作者邮箱：mappstore@163.com
 * 功能描述：bitmapBase压缩64
 * 类    名： TestActivityActivity
 * 备    注：
 */

public class Bitmap2Base642 {

    private static picture2Base64CallBack callBack;

    /**
     * 通过Base32将Bitmap转换成Base64字符串
     *
     * @param exterName      文件的扩展格式
     * @param qualityPercent 压缩的百分比
     * @param bmp
     * @return
     */
    public static String Bitmap2StrByBase64(String exterName, int qualityPercent, Bitmap bmp) {
        Bitmap.CompressFormat pictureType = Bitmap.CompressFormat.JPEG;    //默认使用jpg
        if ("PNG".equalsIgnoreCase(exterName)) {    //判断是不是png格式的
            pictureType = Bitmap.CompressFormat.PNG;
        }
        if (qualityPercent < 10) {   //将图片的压缩质量控制在10-100之间
            qualityPercent = 10;
        } else if (qualityPercent > 100) {
            qualityPercent = 100;
        }

        // 尺寸压缩倍数,值越大，图片尺寸越小
        int ratio = getRatioSize(bmp, 960, 480);  //在这里可以对图片目标尺寸进行配置
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap(bmp.getWidth() / ratio, bmp.getHeight() / ratio, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bmp.getWidth() / ratio, bmp.getHeight() / ratio);
        canvas.drawBitmap(bmp, null, rect, null);


        // 把压缩后的数据存放到baos中

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //对副本进行压缩
//        result.compress(pictureType, qualityPercent, bos);//参数100表示不压缩
        result.compress(pictureType, qualityPercent, bos);
        LogUtil.e("压缩前的文件大小为：------- " + bos.toByteArray().length + "  byte  ");
        while (bos.toByteArray().length / 1024 > 230) {   //循环压缩知道图片在范围内
            bos.reset();
            result.compress(pictureType, qualityPercent, bos);
            LogUtil.e("图片压缩中产生的大小：------------ " + bos.toByteArray().length + "  byte  ");
            qualityPercent -= 10;
            if (qualityPercent < 10) {   //压缩质量不能太小啊
                break;
            }
            LogUtil.e("压缩质量：-------" + qualityPercent);
        }


        byte[] bytes = bos.toByteArray();

        LogUtil.e("现有的文件大小为：------- " + bytes.length + "  byte  ");
        try {   //使用完成之后关闭流
            bmp.recycle();//自由选择是否进行回收
        } catch (Exception e) {
            LogUtil.e("Bitmap2Base64 流关闭异常");
        }

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    /**
     * 根据图片的尺寸获取压缩比
     *
     * @param targetWidth  目标压缩的宽度
     * @param targetHeight 目标压缩的高度
     * @return
     */
    public static int getRatioSize(Bitmap bmp, int targetWidth, int targetHeight) {
        int bitWidth = bmp.getWidth();
        int bitHeight = bmp.getHeight();
        // 图片最大分辨率
        if (targetWidth == 0) {
            targetWidth = 960;
        }
        if (targetHeight == 0) {
            targetHeight = 1080;
        }


        // 缩放比
        int ratio = 1;
        // 缩放比,由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        if (bitWidth > bitHeight && bitWidth > targetWidth) {
            // 如果图片宽度比高度大,以宽度为基准
            ratio = bitWidth / targetWidth;
        } else if (bitWidth < bitHeight && bitHeight > targetHeight) {
            // 如果图片高度比宽度大，以高度为基准
            ratio = bitHeight / targetHeight;
        }
        // 最小比率为1
        if (ratio <= 0) {
            ratio = 1;
        }
        return ratio;
    }


    /**
     * 将一个文件转码成 base64字符串，文件不存在和文件转换错误统一回传null 调用者自行判断处理
     *
     * @param filePath 需要转换文件的绝对路径
     * @return
     */
    public static String File2StrByBase64(String filePath) {
        File file = new File(filePath);
        if (TextUtils.isEmpty(filePath) || (!file.exists())) {
            return null;
        }

        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            String encodedString = Base64.encodeToString(buffer, Base64.DEFAULT);
            return encodedString;
        } catch (Exception e) {
            LogUtil.e("文件转换base64转换异常;\n 类名 Bitmap2Base64 \n错误原因:", e);
            return null;
        }
    }

    /**
     * 将图片转换成对用的字符串
     * 正图生活邻里圈专用
     * 转换后的格式
     * {
     * "jsonStr": [
     * {
     * "type": "jpg",
     * "base64": "asd/9j/4AAQSkZJRgAQA"
     * }
     * ]
     * }
     *
     * @param localMedias
     * @return 如果集合没有内容将会返回空
     */
//    public static String picture2String(ArrayList<LocalMedia> localMedias) {
//        //{"jsonStr":[{"type":"jpg","base64":"
//        StringBuilder pictureSb = new StringBuilder();
//
//        //拼接{"jsonStr":[{"type":"jpg","base64":"
//        if (localMedias == null || localMedias.size() == 0) {   //集合没有数据直接返回null
//            return "";
//        }
//
//
//        pictureSb.append("{\"jsonStr\":[");
//        for (int i = 0; i < localMedias.size(); i++) {
//            String path = localMedias.get(i).getCompressPath();
//            String exterName = getExterName(path);      //文件的扩展名
//
//            String s = Bitmap2Base64.Bitmap2StrByBase64(exterName, 100, PictureUtils.getLoacalBitmap(path));
//
//
//            if (!TextUtils.isEmpty(s)) {
//                pictureSb.
//                        append("{\"type\":\"").
//                        append(exterName).    //拼接扩展名
//                        append("\",\"base64\":\"");
//                //拼接"}]}
//                pictureSb.append(s).append("\"}");
//                if (i != localMedias.size() - 1) {               //拼接逗号最后一条特殊
//                    pictureSb.append(",");
//                }
//            }
//        }
//        pictureSb.append("]}");
//
//        return pictureSb.toString();
//    }

    /**
     * 将图片转换成对用的字符串
     * 正图生活邻里圈专用
     * 转换后的格式
     * {
     * "jsonStr": [
     * {
     * "type": "jpg",
     * "base64": "asd/9j/4AAQSkZJRgAQA"
     * }
     * ]
     * }
     *
     * //@param localMedias
     * @return 如果集合没有内容将会返回空
     */
//    public static String picture2String(ArrayList<LocalMedia> localMedias, Context context) {
//        //{"jsonStr":[{"type":"jpg","base64":"
//        StringBuilder pictureSb = new StringBuilder();
//
//        //拼接{"jsonStr":[{"type":"jpg","base64":"
//        if (localMedias == null || localMedias.size() == 0) {   //集合没有数据直接返回null
//            return "";
//        }
//
//
//        pictureSb.append("{\"jsonStr\":[");
//        for (int i = 0; i < localMedias.size(); i++) {
//            String path = localMedias.get(i).getCompressPath();
//            String exterName = getExterName(path);      //文件的扩展名
//
//            String s = Bitmap2Base64.Bitmap2StrByBase64(exterName, 100, PictureUtils.getLoacalBitmap(path));
//
//            //数据存储
////            saveFile(context,FileUtils.getSDCardPath4Setting(MApplication.sAppContext)+"/"+Util.getDateStr(),s);
//            //数据存储
//           /* FileUtils.textFileWrite(FileUtils.getSDCardPath4Setting(MApplication.sAppContext) +
//                    "/" +Util.getDateStr(), s);*/
//
//            if (!TextUtils.isEmpty(s)) {
//                pictureSb.
//                        append("{\"type\":\"").
//                        append(exterName).    //拼接扩展名
//                        append("\",\"base64\":\"");
//                //拼接"}]}
//                pictureSb.append(s).append("\"}");
//                if (i != localMedias.size() - 1) {               //拼接逗号最后一条特殊
//                    pictureSb.append(",");
//                }
//            }
//        }
//        pictureSb.append("]}");
//
//        return pictureSb.toString();
//    }


    //保存数据
    public static boolean saveFile(Context context, String filePath, String content) {
        //创建文件对象
        //File file = new File("/data/data/cn.yzx.login", "info.txt");

        //创建文件对象 通过file目录
        //File file = new File(context.getFilesDir(),"info.txt");

        //创建文件对象 通过cache目录
        File file = new File(filePath);
        LogUtil.e("文件存储路径：----" + filePath);
        try {
            //文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            //写数据
            fos.write(content.getBytes());
//            LogUtil.e("文件名存储成功：----");
            //关闭文件流
            fos.close();
            return true;
        } catch (Exception e) {
//            LogUtil.e("文件名存储失败：----");
//            LogUtil.e("文件名存储失败：----",e);
            e.printStackTrace();

            return false;
        }
    }


    /**
     * 将图片转换成对用的字符串
     * 正图生活物业专用
     * 转换后的格式
     * {
     * "jsonStr": [
     * {
     * "type": "jpg",
     * "base64": "asd/9j/4AAQSkZJRgAQA"
     * }
     * ]
     * }
     *
     * @param localMedias
     * @return 如果集合没有内容将会返回空
     */
//    public static String communityPicture2String(ArrayList<LocalMedia> localMedias) {
//        //{"jsonStr":[{"type":"jpg","base64":"
//        StringBuilder pictureSb = new StringBuilder();
//
//        //拼接{"jsonStr":[{"type":"jpg","base64":"
//        if (localMedias == null || localMedias.size() == 0) {   //集合没有数据直接返回null
//            return null;
//        }
//
//        pictureSb.append("{\"list\":[");
//        for (int i = 0; i < localMedias.size(); i++) {
//            String path = localMedias.get(i).getCompressPath();
//            String exterName = getExterName(path);      //文件的扩展名
//
//            String s = Bitmap2Base64.Bitmap2StrByBase64(exterName, 100, PictureUtils.getLoacalBitmap(path));
//            if (!TextUtils.isEmpty(s)) {
//                pictureSb.
//                        append("{\"type\":\"").
//                        append(exterName).    //拼接扩展名
//                        append("\",\"image\":\"");
//                //拼接"}]}
//                pictureSb.append(s).append("\"}");
//                if (i != localMedias.size() - 1) {               //拼接逗号最后一条特殊
//                    pictureSb.append(",");
//                }
//            }
//        }
//        pictureSb.append("]}");
//
//        return pictureSb.toString();
//    }

    /**
     * 将图片转换成对用的字符串
     * 正图生活用户头像修改专用
     * 转换后的格式
     * {
     * "type":"png",
     * "base64":"1545454sdasd"}
     *
     * @param path
     * @return 如果集合没有内容将会返回空
     */
//    public static String userIconPicture2String(String path) {
//        //{"image":{"type":"jpg","base64":"
//        StringBuilder pictureSb = new StringBuilder();
//
//        //拼接{"image":{"type":"jpg","base64":"
//        if (TextUtils.isEmpty(path)) {   //集合没有数据直接返回null
//            return null;
//        }
//
//
//        String exterName = getExterName(path);      //文件的扩展名
//        String s = Bitmap2Base64.Bitmap2StrByBase64(exterName, 100, PictureUtils.getLoacalBitmap(path));
//        if (!TextUtils.isEmpty(s)) {
//            pictureSb.
//                    append("{\"type\":\"").
//                    append(exterName).    //拼接扩展名
//                    append("\",\"base64\":\"").
//                    append(s).
//                    append("\"}"); //拼接"}]}
//        }
//
//        return pictureSb.toString();
//    }

    /**
     * 获取文件的扩展名
     * 1.   在Android应用中运行的分割字符串不能写成split（“|”）；
     * 2.	在Android应用中运行的分割字符串得加上中括号split（“[ | ]”）；
     * 3.	总结，使用字符分割的代码如果是在JDK的环境中运行就是用split（“|”）；如果是在Android运行环境中的话使用split（“[ | ]”）
     * 示例1：
     *
     * @return
     */
    public static String getExterName(String path) {
        String extendName;
        File file = new File(path);
        String fileName = file.getName();
        String[] token = fileName.split("[.]");
        extendName = token[token.length - 1];     //获取扩展名
        if (TextUtils.isEmpty(extendName)) {
            extendName = "jpg";
        }
        return extendName;
    }


    /**
     * 2. 尺寸压缩，并将图片文件写入本地文件中
     * <p>
     * 通过缩放图片像素来减少图片占用内存大小
     */
    public static void compressBitmapToFile(Bitmap bmp, File file) {
        // 尺寸压缩倍数,值越大，图片尺寸越小
        int ratio = getRatioSize(bmp, 960, 480);
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap(bmp.getWidth() / ratio, bmp.getHeight() / ratio, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bmp.getWidth() / ratio, bmp.getHeight() / ratio);
        canvas.drawBitmap(bmp, null, rect, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//----------------------------使用最新的压缩计算方法------------------------------------------------



    public interface picture2Base64CallBack {

        void successCallBack(String key, String callBack);

        void failedCallBack(String message, String callBack);
    }


    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    private static void setupOriginInfo(Bitmap[] bitmap, Context context) {
        for (int i = 0; i < bitmap.length; i++) {
            setupOriginInfo(bitmap[i], context);
        }
    }

    private static void setupOriginInfo(Bitmap bitmap, Context context) {
        LogUtil.e("origin bitmap memory size:" + Formatter.formatFileSize(context, bitmap.getByteCount())
                + "\nwidth:" + bitmap.getWidth() + ",height:" + bitmap.getHeight() + ",config:" + bitmap.getConfig());

    }

    private static void setupCompressInfo(Bitmap[] bitmap, Context context) {
        for (int i = 0; i < bitmap.length; i++) {
            setupCompressInfo(bitmap[i], context);
        }
    }

    private static void setupCompressInfo(Bitmap bitmap, Context context) {

        LogUtil.e("compress bitmap memory size:" + Formatter.formatFileSize(context, bitmap.getByteCount())
                + "\nwidth:" + bitmap.getWidth() + ",height:" + bitmap.getHeight() + ",config:" + bitmap.getConfig());
        LogUtil.e("-----------------------------------------------");
    }

    private static void gcAndFinalize() {
        Runtime runtime = Runtime.getRuntime();
        System.gc();
        runtime.runFinalization();
        System.gc();
    }

}
