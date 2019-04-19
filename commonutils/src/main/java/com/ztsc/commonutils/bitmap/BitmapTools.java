package com.ztsc.commonutils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by benchengzhou on 2017/3/28 11:13 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： BitmapTools
 * 备    注： 图片处理工具类
 */

public class BitmapTools {


    /**
     * 等比缩放获取一个圆形图片
     * @param bitmap
     * @return
     */
    public static Bitmap getOvalBitmap(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final Rect rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final Rect rectDst;
        if (bitmap.getWidth() < bitmap.getHeight()) {
            rectDst = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());
        } else {
            rectDst = new Rect(0, 0, bitmap.getHeight(), bitmap.getHeight());
        }


//        final Rect rectDst = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rectSrc);
        final RectF rectFDst = new RectF(rectDst);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawOval(rectFDst, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rectSrc, rectFDst, paint);
        return output;
    }


    /**
     * 将已有的bitmap转换成圆角，默认是在原有矩形的大小尺寸基础上截取圆形，如果withReduce，
     * hightReduce不为0则会在原有的基础上减少对应的像素值
     *
     * @param bitmap      原始样式和大小的bitmap
     * @param withReduce  横向减少量
     * @param hightReduce 高度减少量
     * @return
     */
    public static Bitmap getOvalBitmapAtZoomSize(Bitmap bitmap, int withReduce, int hightReduce) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        if (withReduce != 0) {
            withReduce = withReduce / 2;
        }
        if (hightReduce != 0) {
            hightReduce = hightReduce / 2;
        }
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final Rect rect = new Rect(withReduce, hightReduce, bitmap.getWidth() - hightReduce, bitmap.getHeight() - hightReduce);

        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

}
