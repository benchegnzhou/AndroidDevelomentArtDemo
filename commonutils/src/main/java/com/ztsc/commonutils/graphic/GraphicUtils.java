package com.ztsc.commonutils.graphic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * https://www.zhihu.com/
 * _____                    _____                    _____                    _____
 * /\    \                  /\    \                  /\    \                  /\    \
 * /::\____\                /::\    \                /::\    \                /::\    \
 * /:::/    /                \:::\    \              /::::\    \              /::::\    \
 * /:::/    /                  \:::\    \            /::::::\    \            /::::::\    \
 * /:::/    /                    \:::\    \          /:::/\:::\    \          /:::/\:::\    \
 * /:::/____/                      \:::\    \        /:::/__\:::\    \        /:::/__\:::\    \
 * /::::\    \                      /::::\    \      /::::\   \:::\    \      /::::\   \:::\    \
 * /::::::\    \   _____    ____    /::::::\    \    /::::::\   \:::\    \    /::::::\   \:::\    \
 * /:::/\:::\    \ /\    \  /\   \  /:::/\:::\    \  /:::/\:::\   \:::\____\  /:::/\:::\   \:::\    \
 * /:::/  \:::\    /::\____\/::\   \/:::/  \:::\____\/:::/  \:::\   \:::|    |/:::/__\:::\   \:::\____\
 * \::/    \:::\  /:::/    /\:::\  /:::/    \::/    /\::/   |::::\  /:::|____|\:::\   \:::\   \::/    /
 * \/____/ \:::\/:::/    /  \:::\/:::/    / \/____/  \/____|:::::\/:::/    /  \:::\   \:::\   \/____/
 * \::::::/    /    \::::::/    /                 |:::::::::/    /    \:::\   \:::\    \
 * \::::/    /      \::::/____/                  |::|\::::/    /      \:::\   \:::\____\
 * /:::/    /        \:::\    \                  |::| \::/____/        \:::\   \::/    /
 * /:::/    /          \:::\    \                 |::|  ~|               \:::\   \/____/
 * /:::/    /            \:::\    \                |::|   |                \:::\    \
 * /:::/    /              \:::\____\               \::|   |                 \:::\____\
 * \::/    /                \::/    /                \:|   |                  \::/    /
 * \/____/                  \/____/                  \|___|                   \/____/
 */


/**
 * 屏幕像素点和图片尺寸转换的工具类
 */
public final class GraphicUtils {
    private final static String TAG = "GraphicUtils";
    public static int mScreenWidth;
    public static int mScreenHeight;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 获取这个手机的密度比
     */
    public static float getDensity(Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return scale;
    }


    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getDefaultDisplay(context).getWidth();
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getDefaultDisplay(context).getHeight();
    }

    public static Display getDefaultDisplay(Context context) {
        WindowManager wm = ((Activity) context).getWindowManager();
        return wm.getDefaultDisplay();
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    public static Bitmap createRoundImage(Context context, Bitmap originalImage, Bitmap mask) {
        RectF clipRect = new RectF();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Bitmap roundBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(roundBitmap);
        canvas.drawBitmap(originalImage, 0, 0, null);
        clipRect.set(0, 0, width, height);
        canvas.drawBitmap(mask, null, clipRect, null);
        return roundBitmap;
    }

    public static Bitmap createRoundImage(Context context, Bitmap originalImage, int dp) {
        final int CONNER = dip2px(context, dp);
        RectF clipRect = new RectF();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Bitmap roundBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(roundBitmap);
        Path path = new Path();
        clipRect.set(0, 0, width, height);
        path.addRoundRect(clipRect, CONNER, CONNER, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawBitmap(originalImage, null, clipRect, null);
        return roundBitmap;
    }

    public static Bitmap createReflectedImage(Context context, Bitmap originalImage, Bitmap mask) {
        final int reflectionGap = 1;
        final int CONNER = dip2px(context, 5);
        Path path = new Path();
        RectF clipRect = new RectF();

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap
                .createBitmap(originalImage, 0, height * 3 / 4, width, height / 4, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 4), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);
        clipRect.set(0, 0, width, height);
        canvas.drawBitmap(mask, null, clipRect, null);

        canvas.save();
        clipRect.set(0, height, width, (height + height / 4 + CONNER) + reflectionGap);
        path.addRoundRect(clipRect, CONNER, CONNER, Path.Direction.CCW);
        canvas.clipPath(path);

        Paint point = new Paint();
        point.setColor(Color.LTGRAY);
        canvas.drawRect(clipRect, point);
        canvas.restore();

        canvas.save();
        clipRect.set(1, height + 1, width - 1, (height + height / 4 + CONNER) + reflectionGap);
        path.reset();
        path.addRoundRect(clipRect, CONNER, CONNER, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        canvas.restore();

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, height, 0, bitmapWithReflection.getHeight() + reflectionGap,
                0x50000000, 0x00000000, TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    public final static int LOCATION_LEFT_TOP = 0;
    public final static int LOCATION_BOTTOM_RIGHT = 1;

    public static final Point getViewPosition(View view, int type) {
        int[] locations = new int[2];
        view.getLocationInWindow(locations);
        Point point = new Point(locations[0], locations[1]);
        if (type == LOCATION_LEFT_TOP) {
            return point;
        }
        if (type == LOCATION_BOTTOM_RIGHT) {
            int width = view.getWidth();
            int height = view.getHeight();
            point.x += width;
            point.y += height;
            return point;
        }
        return new Point(0, 0);
    }

    public static Bitmap getBitmapByBitmap(Bitmap backBitmap, Bitmap mask, int x, int y) {
        Bitmap output = Bitmap.createBitmap(backBitmap.getWidth(), backBitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, backBitmap.getWidth(), backBitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawBitmap(mask, x, y, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(backBitmap, rect, rect, paint);
        return output;
    }

    public final static boolean pointInView(Point point, View view) {
        Point left_top = GraphicUtils.getViewPosition(view, GraphicUtils.LOCATION_LEFT_TOP);
        Point bottom_right = GraphicUtils.getViewPosition(view, GraphicUtils.LOCATION_BOTTOM_RIGHT);
        return (point.x >= left_top.x && point.x <= bottom_right.x && point.y >= left_top.y && point.y <= bottom_right.y);
    }

    // 图形配比
    public static Bitmap getImageScale(Bitmap bitmap, int width, int height) {
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();
        float scaleWidth = ((float) width) / srcWidth;
        float scaleHeight = ((float) height) / srcHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap destbmp = Bitmap.createBitmap(bitmap, 0, 0, srcWidth, srcHeight, matrix, true);
        return destbmp;
    }

    public final static Bitmap getImageScaleMessage(Context context, Bitmap bmp, int width, int height) {
        if (bmp == null) {
            return null;
        }
        int widthMax = GraphicUtils.dip2px(context, width);
        int heightMax = GraphicUtils.dip2px(context, height);
        Bitmap resultBmp = null;

        try {
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inJustDecodeBounds = true;

            int sampleSize = 1;
            option.inJustDecodeBounds = false;
            int widthRatio = option.outWidth / widthMax;
            int heightRatio = option.outHeight / heightMax;
            if (widthRatio > 1 || heightRatio > 1) {
                sampleSize = Math.max(widthRatio, heightRatio);
                option.inSampleSize = sampleSize;
            }
            int newWidth = bmp.getWidth();
            int newHeight = bmp.getHeight();
            if (newWidth <= widthMax && newHeight <= heightMax) {
                return bmp;
            }

            float scaleWidth = ((float) widthMax) / newWidth;
            float scaleHeight = ((float) heightMax) / newHeight;
            float scale = Math.min(scaleWidth, scaleHeight);

            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);

            resultBmp = Bitmap.createBitmap(bmp, 0, 0, newWidth, newHeight, matrix, true);

            if (bmp != null) {
                bmp.recycle();
            }
        } catch (Exception e) {
            return null;
        }
        return resultBmp;
    }

    /**
     * @param context
     * @param uri
     * @param width
     * @param height
     * @return Bitmap
     * @Title: getImageThumbnail
     * @Description: 得到缩略图(单位是像素值)
     */
    public final static Bitmap getImageThumbnail(Context context, Uri uri, int width, int height) {
        if (uri == null) {
            return null;
        }
        return getImageThumbnail(context, uri.getPath(), width, height);
    }

    /**
     * 创建缩略图
     *
     * @param context  上下文
     * @param bitmap   原Bitmap
     * @param dpWidth  缩略图的宽度
     * @param dpHeight 缩略图的高度
     * @return 返回缩略图 Bitmap
     * @Title: getImageThumbnail
     * @Description: TODO
     */
    public final static Bitmap getImageThumbnail(Context context, Bitmap bitmap, int dpWidth, int dpHeight) {
        Bitmap resultBmp = null;
        int widthMax = GraphicUtils.dip2px(context, dpWidth);
        int heightMax = GraphicUtils.dip2px(context, dpHeight);

        int newWidth = bitmap.getWidth();
        int newHeight = bitmap.getHeight();
        if (newWidth <= widthMax && newHeight <= heightMax) {
            return bitmap;
        }
        float scaleWidth = ((float) widthMax) / newWidth;
        float scaleHeight = ((float) heightMax) / newHeight;
        float scale = Math.min(scaleWidth, scaleHeight);

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        resultBmp = Bitmap.createBitmap(bitmap, 0, 0, newWidth, newHeight, matrix, true);

        if (bitmap != null) {
            bitmap.recycle();
        }
        return resultBmp;
    }

    /**
     * @param context
     * @param filePath
     * @param width
     * @param height
     * @return Bitmap
     * @Title: getImageThumbnail
     * @Description: 得到缩略图(单位是dp值)
     */
    @Deprecated
    public final static Bitmap getImageThumbnail(Context context, String filePath, int width, int height) {
        int widthMax = GraphicUtils.dip2px(context, width);
        int heightMax = GraphicUtils.dip2px(context, height);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = false;
        if (widthMax > 0 && heightMax > 0) {
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, opts);
            // 计算图片缩放比例
            final int minSideLength = Math.min(widthMax, heightMax);
            opts.inSampleSize = computeSampleSize(opts, minSideLength, widthMax * heightMax);
        } else {
            opts.inSampleSize = 4;
        }
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Config.ARGB_8888;
        opts.inInputShareable = true;
        opts.inPurgeable = true;

        File file = new File(filePath);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
        }
        Bitmap bmp = null;
        if (fs != null) {
            try {
                bmp = BitmapFactory.decodeStream(fs, null, opts);
            } catch (OutOfMemoryError e) {
                System.gc();
            } finally {
                try {
                    fs.close();
                } catch (IOException e) {
                }
            }
        }
        if (bmp == null) {
            System.gc();
        }
        return bmp;
    }

    public final static Bitmap getImageThumbnailByDip(Context context, String filePath, int width, int height) {
        int widthMax = GraphicUtils.dip2px(context, width);
        int heightMax = GraphicUtils.dip2px(context, height);
        return getImageThumbnailByPixel(context, filePath, widthMax, heightMax);
    }

    public final static Bitmap getImageThumbnailByPixel(Context context, String filePath, int width, int height) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = false;
        if (width > 0 && height > 0) {
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, opts);
            // 计算图片缩放比例
            final int minSideLength = Math.min(width, height);
            opts.inSampleSize = computeSampleSize(opts, minSideLength, width * height);
        } else {
            opts.inSampleSize = 4;
        }
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Config.ARGB_8888;
        opts.inInputShareable = true;
        opts.inPurgeable = true;
        File file = new File(filePath);
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
        }
        Bitmap bmp = null;
        if (fs != null) {
            try {
                bmp = BitmapFactory.decodeStream(fs, null, opts);
            } catch (OutOfMemoryError e) {
                System.gc();
            } finally {
                try {
                    fs.close();
                } catch (IOException e) {
                }
            }
        }
        if (bmp == null) {
            System.gc();
        }
        return bmp;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;// <= 1 ? 2 : roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
                Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public final static int AT_TOP = 0;
    public final static int AT_BOTTOM = 1;
    public final static int OTHER = 2;

    public static final int PULL_TO_REFRESH = 3;
    public static final int RELEASE_TO_REFRESH = 4;
    public static final int REFRESHING = 5;
    public static final int FRESH_OTHER = 6;

    /**
     * @param bmpOriginal
     * @return Bitmap
     * @Title: toGrayscale
     * @Description: 灰度图
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);

        return bmpGrayscale;
    }

    /**
     * @param color
     * @return int
     * @Title: getColor
     * @Description: 得到RGB颜色值
     */
    public static int getColor(int color) {
        return Color.rgb(color >> 16, (color & 0x00ffff) >> 8, color & 0x0000ff);
    }

    /**
     * @param context
     * @param uri
     * @return Bitmap
     * @Title: getImage
     * @Description: 加载图片
     */
    public final static Bitmap getImage(Context context, Uri uri) {
        Bitmap resultBmp = null;
        try {
            String path = uri.getPath();
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inJustDecodeBounds = true;
            Bitmap bmp;
            option.inJustDecodeBounds = false;
            option.inSampleSize = 2;
            bmp = BitmapFactory.decodeFile(path, option);
            if (bmp == null) {
                bmp = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, option);
            }
            resultBmp = bmp;
            return resultBmp;
        } catch (Exception e) {
            return null;
        } catch (OutOfMemoryError e) {
            System.gc();
            return null;
        }
    }

    /**
     * @param imagePaths   要拼接的图片路径
     * @param format       文件格式(jpg、png)
     * @param quality      压缩质量
     * @param destFileName 拼接后图片文件名
     * @return boolean
     * @Title: combineImage
     * @Description: 图片拼接(将多张图片拼接成一个图片)
     */
    public static boolean combineImage(String[] imagePaths, CompressFormat format, int quality, String destFileName) {
        boolean result = false;
        int outHeight = 0;
        int outWidth = 0;
        ArrayList<Bitmap> srcBitmapList = new ArrayList<Bitmap>(imagePaths.length);
        // 计算拼接后图片的高度
        for (int index = 0; index < imagePaths.length; ++index) {
            Bitmap srcBitmap = BitmapFactory.decodeFile(imagePaths[index]);
            if (srcBitmap.getWidth() > outWidth) {
                outWidth = srcBitmap.getWidth();
            }
            outHeight += srcBitmap.getHeight();
            srcBitmapList.add(srcBitmap);
        }
        // 创建拼接结果的缩略图
        Bitmap destBitmap = Bitmap.createBitmap(outWidth, outHeight, Config.ARGB_8888);

        Canvas canvas = new Canvas(destBitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        // 对位图进行滤波处理
        paint.setFilterBitmap(true);

        int top = 0;
        for (int index = 0; index < imagePaths.length; ++index) {
            Bitmap srcBitmap = srcBitmapList.get(index);
            Rect srcRect = new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());
            Rect destRect = new Rect();
            destRect.set(0, top, srcBitmap.getWidth(), srcBitmap.getHeight() + top);
            // 把图片srcBit上srcRect画到destRect的区域内
            canvas.drawBitmap(srcBitmap, srcRect, destRect, paint);
            top += srcBitmap.getHeight();
            srcBitmap.recycle();
        }
        srcBitmapList.clear();
        File destFile = new File(destFileName);
        if (destFile != null) {
            // 经过图像变换之后的Bitmap里的数据可以保存到图像压缩文件里（JPG/PNG）。
            // 参数format可设置JPEG或PNG格式；
            // quality可选择压缩质量；fOut是输出流（OutputStream）
            result = saveBitmapFile(destBitmap, format, quality, destFileName);
        }
        return result;
    }

    /**
     * @param bitmap
     * @param format       图片格式
     * @param quality      压缩质量
     * @param destFileName 文件名
     * @return boolean true:成功
     * @Title: saveBitmapFile
     * @Description: 保存bitmap到磁盘文件
     */
    public static boolean saveBitmapFile(Bitmap bitmap, CompressFormat format, int quality, String destFileName) {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(destFileName);
            bitmap.compress(format, quality, fos);
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return false;
    }

    /**
     * @param byt 数据流
     * @return Bitmap 位图
     * @Title: bytes2Bimap
     * @Description: 将数据流转化为位图
     */
    public static Bitmap bytes2Bimap(byte[] byt) {
        if (byt.length != 0) {
            return BitmapFactory.decodeByteArray(byt, 0, byt.length);
        } else {
            return null;
        }
    }

    public static Bitmap getViewBitmap(View view) {
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    //	平铺背景图片的工具类
    public static BitmapDrawable createRepeater(Activity act, int src) {
        Bitmap bitmap = BitmapFactory.decodeResource(act.getResources(), src);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
        drawable.setDither(true);
        return drawable;
    }

    /**
     * 高斯模糊
     */
    public static Bitmap doBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }


    public static void compressImage(Bitmap image, String scrPath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 200) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        try {
            FileOutputStream fos = new FileOutputStream(scrPath);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.recycle();
    }


    /**
     * 判断矩形A与矩形B是否相交传入矩形的左上点和右下点
     * 参考： https://blog.csdn.net/szfhy/article/details/49740191
     *
     * @param aLeftTopPoint
     * @param aRightBottomPoint
     * @param bLeftTopPoint
     * @param bRightBottomPoint
     * @return
     */
    public static boolean isRectIntersect(Point aLeftTopPoint, Point aRightBottomPoint,
                                          Point bLeftTopPoint, Point bRightBottomPoint) {
        return isRectIntersect(aLeftTopPoint.x, aRightBottomPoint.x, aLeftTopPoint.y, aRightBottomPoint.y,
                bLeftTopPoint.x, bRightBottomPoint.x, bLeftTopPoint.y, bRightBottomPoint.y);
    }


    /**
     * 判断矩形A与矩形B是否相交
     * 参考： https://blog.csdn.net/szfhy/article/details/49740191
     *
     * @param aLeft
     * @param aRight
     * @param aTop
     * @param aBottom
     * @param bLeft
     * @param bRight
     * @param bTop
     * @param bBottom
     * @return
     */
    public static boolean isRectIntersect1(int aLeft, int aRight, int aTop, int aBottom,
                                           int bLeft, int bRight, int bTop, int bBottom) {
        int zx = Math.abs(aLeft + aRight - bLeft - bRight);
        int x = Math.abs(aLeft - aRight) + Math.abs(bLeft - bRight);
        int zy = Math.abs(aTop + aBottom - bTop - bBottom);
        int y = Math.abs(aTop - aBottom) + Math.abs(bTop - bBottom);
        return (zx <= x && zy <= y);

    }

    public static boolean isRectIntersect(int aLeft, int aRight, int aTop, int aBottom,
                                          int bLeft, int bRight, int bTop, int bBottom) {

        int nLeft = 0;
        int nTop = 0;
        int nRight = 0;
        int nBottom = 0;

        //計算兩矩形可能的相交矩形的邊界
        nLeft = aLeft >= bLeft ? aLeft : bLeft;
        nTop = aTop >= bTop ? aTop : bTop;
        nRight = aRight >= bRight ? bRight : aRight;
        nBottom = aBottom >= bBottom ? bBottom : aBottom;
        // 判斷是否相交
        return (nLeft < nRight && nTop < nBottom);
    }
}
