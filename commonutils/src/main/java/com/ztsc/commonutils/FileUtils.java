package com.ztsc.commonutils;

import android.content.Context;
import android.text.TextUtils;

import com.ztsc.commonutils.logcat.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class FileUtils {

    private static final String TAG = "Wth2:FileUtils";

    public static String getStringFromFile(String filePath) {
        FileInputStream is = null;
        try {
            File file = new File(filePath);
            is = new FileInputStream(file);
            return getStringFromInputStream(is);
        } catch (Exception e) {
            Logger.e(TAG, "getStringFromFile() ", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                Logger.e(TAG, "getStringFromFile() when close file", e);
            }
        }
        return "";
    }

    public static boolean copyAssetsFilesRecursivelyToDisk(Context context, String assetsDir, String targetDir) {
        Logger.d(TAG, String.format("copyAssetsFilesRecursivelyToDisk(): assetsDir=%s targetDir=%s", assetsDir, targetDir));
        boolean result = true;
        if (TextUtils.isEmpty(targetDir)) {
            Logger.w(TAG, "copyAssetsFilesRecursivelyToDisk():targetDir is empty");
            return false;
        }

        if (TextUtils.isEmpty(assetsDir)) {
            assetsDir = "";
        }

        try {
            String[] childFileNames = context.getAssets().list(assetsDir);
            if (childFileNames.length > 0) {
                //当前为文件夹
                File targetFileDir = new File(targetDir);
                if (!targetFileDir.exists()) {
                    Logger.d(TAG, "copyAssetsFilesRecursivelyToDisk(): mkdirs:" + targetDir);
                    result = result && targetFileDir.mkdirs();
                }
                for (String childFileName : childFileNames) {
                    String childFileDir = "";
                    if (!TextUtils.isEmpty(assetsDir)) {
                        childFileDir = String.format("%s%s%s", assetsDir, File.separator, childFileName);
                    } else {
                        childFileDir = childFileName;
                    }
                    copyAssetsFilesRecursivelyToDisk(context, childFileDir, targetDir + File.separator + childFileName);
                }
            } else {
                result = result && copyAssetsFileToDisk(context, assetsDir, targetDir);
            }

        } catch (Exception e) {
            Logger.e(TAG, "copyAssetsFilesRecursivelyToDisk()", e);
        }
        return result;
    }

    public static boolean copyAssetsFileToDisk(Context context, String from, String to) {
        String dirPath = to.substring(0,to.lastIndexOf(File.separator));
        File dir = new File(dirPath);
        if (!dir.exists()) {
            Logger.d(TAG, "copyAssetsFileToDisk(): mkdirs");
            dir.mkdirs();
        }
        File file = new File(to);
        if (file.exists()) {
            Logger.d(TAG, "copyAssetsFileToDisk(): delete old file");
            file.delete();
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = context.getResources().getAssets().open(from);
            os = new FileOutputStream(file);
            writeInputToOutput(is, os);
        } catch (FileNotFoundException e) {
            Logger.e(TAG, "copyAssetsFileToDisk() fail FileNotFoundException :", e);
            return false;
        } catch (IOException e) {
            Logger.e(TAG, "copyAssetsFileToDisk() fail IOException :", e);
            return false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                Logger.e(TAG, "copyAssetsFileToDisk() when close file", e);
            }
        }
        return true;
    }

    public static boolean existFile(String path) {
        File file = new File(path);
        return file.exists();
    }

    private static String getStringFromInputStream(InputStream is) {
        OutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            writeInputToOutput(is, os);
            return os.toString();
        } catch (Exception e) {
            Logger.e(TAG, "getStringFromInputStream()", e);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                Logger.e(TAG, "getStringFromInputStream() when close OutputStream:", e);
            }
        }
        return "";
    }

    private static void writeInputToOutput(InputStream is, OutputStream os) throws IOException {
        int len = -1;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
            os.flush();
        }
    }
}
