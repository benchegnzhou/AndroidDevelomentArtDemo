package com.ztsc.commonutils.bitmap

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import com.blankj.utilcode.util.CloseUtils
import com.blankj.utilcode.util.ConvertUtils
import com.ztsc.commonutils.logcat.Logger
import java.io.*
import java.nio.charset.Charset


/**
 * Created by benchengzhou on 2017/8/27 23:25 .
 * 作者邮箱：mappstore@163.com
 * 功能描述：
 * 类    名： BitmapUtils
 * 备    注： bitmap获取的工具类
 */

class BitmapUtils {

    private constructor()

    companion object {
        /**
         * 将byte[]转换成InputStream
         */
        fun Byte2InputStream(b: ByteArray?): InputStream? {
            return ByteArrayInputStream(b)
        }

        /**
         * 将InputStream转换成byte[]
         */
        fun InputStream2Bytes(inputStream: InputStream): ByteArray? {
            return  input2OutputStream(inputStream)?.toByteArray()?:null
        }

        fun input2OutputStream(var0: InputStream?): ByteArrayOutputStream? {
            return if (var0 == null) {
                null
            } else {
                val var2: Any?
                try {
                    val var1 = ByteArrayOutputStream()
                    val var10 = ByteArray(1024)
                    var var3: Int
                    while (var0.read(var10, 0, 1024).also { var3 = it } != -1) {
                        var1.write(var10, 0, var3)
                    }
                    return var1
                } catch (var8: IOException) {
                    var8.printStackTrace()
                    var2 = null
                } finally {
                    CloseUtils.closeIO(*arrayOf<Closeable>(var0))
                }
                var2 as ByteArrayOutputStream?
            }
        }

        /**
         * 将Bitmap转换成InputStream
         */
        fun bitmap2InputStream(bm: Bitmap): InputStream? {
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            return ByteArrayInputStream(baos.toByteArray())
        }

        /**
         * 将Bitmap转换成InputStream
         */
        fun bitmap2InputStream(
            bm: Bitmap,
            quality: Int
        ): InputStream? {
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, quality, baos)
            return ByteArrayInputStream(baos.toByteArray())
        }

        /**
         * 将InputStream转换成Bitmap
         */
        fun InputStream2Bitmap(inputStream: InputStream?): Bitmap {
            return BitmapFactory.decodeStream(inputStream)
        }

        /**
         * Drawable转换成InputStream
         */
        fun drawable2InputStream(d: Drawable?): InputStream? {
            val bitmap: Bitmap? = d?.let { drawable2Bitmap(it) } ?: null
            return bitmap?.let { bitmap2InputStream(it) } ?: null
        }

        /**
         * InputStream转换成Drawable
         */
        fun inputStream2Drawable(inputStream: InputStream?): Drawable? {
            val bitmap = InputStream2Bitmap(inputStream)
            return bitmap2Drawable(bitmap)
        }

        /**
         * Drawable转换成byte[]
         */
        fun drawable2Bytes(d: Drawable?): ByteArray? {
            val bitmap: Bitmap? = d?.let { drawable2Bitmap(it) } ?: null
            return bitmap?.let { bitmap2Bytes(it) } ?: null
        }

        /**
         * byte[]转换成Drawable
         */
        fun bytes2Drawable(b: ByteArray?): Drawable? {
            val bitmap: Bitmap? = b?.let { bytes2Bitmap(it) } ?: null
            return this.bitmap2Drawable(bitmap)
        }

        /**
         * Bitmap转换成byte[]
         */
        fun bitmap2Bytes(bm: Bitmap): ByteArray? {
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
            return baos.toByteArray()
        }

        /**
         * bmp 转换成 bmpToByteArray
         *
         * @param bmp
         * @param needRecycle
         * @return
         */
        fun bmpToByteArray(bmp: Bitmap, needRecycle: Boolean): ByteArray? {
            val output = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, output)
            if (needRecycle) {
                bmp.recycle()
            }
            val result = output.toByteArray()
            try {
                output.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return result
        }

        /**
         * byte[]转换成Bitmap
         */
        fun bytes2Bitmap(b: ByteArray): Bitmap? {
            return if (b.isNotEmpty())
                BitmapFactory.decodeByteArray(b, 0, b.size)
            else null
        }

        /**
         * Drawable转换成Bitmap
         */
        fun drawable2Bitmap(drawable: Drawable): Bitmap? {
            val bitmap = Bitmap
                .createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
                )
            val canvas = Canvas(bitmap)
            drawable.setBounds(
                0, 0, drawable.intrinsicWidth,
                drawable.intrinsicHeight
            )
            drawable.draw(canvas)
            return bitmap
        }

        /**
         * Bitmap转换成Drawable
         */
        fun bitmap2Drawable(bitmap: Bitmap?): Drawable? {
            val bd =
                BitmapDrawable(bitmap)
            return bd
        }

        fun outputStream2String(outputStream: OutputStream, charsetName: Charset): String? {
            return if (outputStream != null && !isSpace(charsetName.toString())) {
                try {
                    outputStream2Bytes(outputStream)?.let {
                        String(it, charsetName)
                    } ?: null
                } catch (var3: UnsupportedEncodingException) {
                    var3.printStackTrace()
                    null
                }
            } else {
                null
            }
        }

        fun outputStream2Bytes(var0: OutputStream?): ByteArray? {
            return if (var0 == null) null else (var0 as ByteArrayOutputStream).toByteArray()
        }

        fun bytes2OutputStream(var0: ByteArray?): OutputStream? {
            return if (var0 != null && var0.size > 0) {
                var var1: ByteArrayOutputStream? = null
                val var3: Any?
                try {
                    var1 = ByteArrayOutputStream()
                    var1.write(var0)
                    return var1
                } catch (var7: IOException) {
                    var7.printStackTrace()
                    var3 = null
                } finally {
                    CloseUtils.closeIO(*arrayOf<Closeable?>(var1))
                }
                var3 as OutputStream?
            } else {
                null
            }
        }

        /**
         * 将字符串转换成Stream
         */
        fun string2OutputStream(bytes: String?, charsetName: String): OutputStream? {
            return if (bytes != null && !isSpace(charsetName)) {
                try {
                    ConvertUtils.bytes2OutputStream(bytes.toByteArray(charset(charsetName)))
                } catch (var3: UnsupportedEncodingException) {
                    var3.printStackTrace()
                    null
                }
            } else {
                null
            }
        }

        private fun isSpace(var0: String?): Boolean {
            return if (var0 == null) {
                true
            } else {
                var var1 = 0
                val var2 = var0.length
                while (var1 < var2) {
                    if (!Character.isWhitespace(var0[var1])) {
                        return false
                    }
                    ++var1
                }
                true
            }
        }

        /**
         * 保存view副本到本地，给定一个保存的路径，
         *
         * @param view 需要保存的view
         * @param path 目标路径
         * @return
         */
        fun saveViewToFile(view: View, path: String?): String? {
            try {
                val mBitmap = copyViewToBitmap(view)
                val out = FileOutputStream(File(path))
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
                return path
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * 传入需要拷贝的原有的布局
         * 这里最好是在这个基础之上
         * 这种方式不会改变原有view 的位置和大小
         *
         * @param view
         */
        fun copyViewToBitmap(view: View): Bitmap {
            //保存信息的类
            val bean = InfoBean()
            bean.statusBarHeight = bean.originRect.top
            //get Origin View's rect
            view.getGlobalVisibleRect(bean.originRect)
            bean.originWidth = bean.originRect.right - bean.originRect.left
            bean.originHeight = bean.originRect.bottom - bean.originRect.top
            Logger.e("originWidth" + bean.originWidth + "originHeight" + bean.originHeight)
            bean.bitmap = createBitmap(view, bean.originWidth, bean.originHeight)
            return bean.bitmap
        }


        /**
         * 通过catch获取拷贝图的方法
         *
         * @param view
         * @param width
         * @param height
         * @return
         */
        fun createBitmap(view: View, width: Int, height: Int): Bitmap? {
            view.isDrawingCacheEnabled = true //获取它的cache先要通过setDrawingCacheEnable方法把cache开启
            view.measure(
                View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
            )
            view.buildDrawingCache()
            return view.drawingCache
        }
        //------------------------------------------下面是对多张bitmap的处理--------合成一张图-----------

        //------------------------------------------下面是对多张bitmap的处理--------合成一张图-----------
        /**
         * 把两个位图覆盖合成为一个位图，以底层位图的长宽为基准
         *
         * @param backBitmap  在底部的位图
         * @param frontBitmap 盖在上面的位图
         * @return
         */
        fun mergeBitmap(backBitmap: Bitmap?, frontBitmap: Bitmap?): Bitmap? {
            if (backBitmap == null || backBitmap.isRecycled
                || frontBitmap == null || frontBitmap.isRecycled
            ) {
                Logger.e("backBitmap=$backBitmap;frontBitmap=$frontBitmap")
                return null
            }
            val bitmap = backBitmap.copy(Bitmap.Config.ARGB_8888, true)
            val canvas = Canvas(bitmap)
            val baseRect = Rect(0, 0, backBitmap.width, backBitmap.height)
            val frontRect = Rect(0, 0, frontBitmap.width, frontBitmap.height)
            canvas.drawBitmap(frontBitmap, frontRect, baseRect, null)
            return bitmap
        }

        /**
         * 把两个位图覆盖合成为一个位图，左右拼接
         *
         * @param leftBitmap
         * @param rightBitmap
         * @param isBaseMax   是否以宽度大的位图为准，true则小图等比拉伸，false则大图等比压缩
         * @return
         */
        fun mergeBitmap_LR(leftBitmap: Bitmap?, rightBitmap: Bitmap?, isBaseMax: Boolean): Bitmap? {
            if (leftBitmap == null || leftBitmap.isRecycled
                || rightBitmap == null || rightBitmap.isRecycled
            ) {
                Logger.e("leftBitmap=$leftBitmap;rightBitmap=$rightBitmap")
                return null
            }
            var height = 0 // 拼接后的高度，按照参数取大或取小
            height = if (isBaseMax) {
                if (leftBitmap.height > rightBitmap.height) leftBitmap.height else rightBitmap.height
            } else {
                if (leftBitmap.height < rightBitmap.height) leftBitmap.height else rightBitmap.height
            }

            // 缩放之后的bitmap
            var tempBitmapL: Bitmap = leftBitmap
            var tempBitmapR: Bitmap = rightBitmap
            if (leftBitmap.height != height) {
                tempBitmapL = Bitmap.createScaledBitmap(
                    leftBitmap,
                    (leftBitmap.width * 1f / leftBitmap.height * height).toInt(), height, false
                )
            } else if (rightBitmap.height != height) {
                tempBitmapR = Bitmap.createScaledBitmap(
                    rightBitmap,
                    (rightBitmap.width * 1f / rightBitmap.height * height).toInt(), height, false
                )
            }

            // 拼接后的宽度
            val width = tempBitmapL.width + tempBitmapR.width

            // 定义输出的bitmap
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            // 缩放后两个bitmap需要绘制的参数
            val leftRect = Rect(0, 0, tempBitmapL.width, tempBitmapL.height)
            val rightRect = Rect(0, 0, tempBitmapR.width, tempBitmapR.height)

            // 右边图需要绘制的位置，往右边偏移左边图的宽度，高度是相同的
            val rightRectT = Rect(tempBitmapL.width, 0, width, height)
            canvas.drawBitmap(tempBitmapL, leftRect, leftRect, null)
            canvas.drawBitmap(tempBitmapR, rightRect, rightRectT, null)
            return bitmap
        }


        /**
         * 把两个位图覆盖合成为一个位图，上下拼接
         *
         * @param topBitmap
         * @param bottomBitmap
         * @param isBaseMax    是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
         * @return
         */
        fun mergeBitmap_TB(topBitmap: Bitmap?, bottomBitmap: Bitmap?, isBaseMax: Boolean): Bitmap? {
            if (topBitmap == null || topBitmap.isRecycled
                || bottomBitmap == null || bottomBitmap.isRecycled
            ) {
                Logger.e("topBitmap=$topBitmap;bottomBitmap=$bottomBitmap")
                return null
            }
            var width = 0
            width = if (isBaseMax) {
                if (topBitmap.width > bottomBitmap.width) topBitmap.width else bottomBitmap.width
            } else {
                if (topBitmap.width < bottomBitmap.width) topBitmap.width else bottomBitmap.width
            }
            var tempBitmapT: Bitmap = topBitmap
            var tempBitmapB: Bitmap = bottomBitmap
            if (topBitmap.width != width) {
                tempBitmapT = Bitmap.createScaledBitmap(
                    topBitmap, width,
                    (topBitmap.height * 1f / topBitmap.width * width).toInt(), false
                )
            } else if (bottomBitmap.width != width) {
                tempBitmapB = Bitmap.createScaledBitmap(
                    bottomBitmap, width,
                    (bottomBitmap.height * 1f / bottomBitmap.width * width).toInt(), false
                )
            }
            val height = tempBitmapT.height + tempBitmapB.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            val topRect = Rect(0, 0, tempBitmapT.width, tempBitmapT.height)
            val bottomRect = Rect(0, 0, tempBitmapB.width, tempBitmapB.height)
            val bottomRectT = Rect(0, tempBitmapT.height, width, height)
            canvas.drawBitmap(tempBitmapT, topRect, topRect, null)
            canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null)
            return bitmap
        }


        /**
         * 将一张图片水平居中显示在大的背景的中间
         *
         * @param backBitmap
         * @param frontBitmap
         * @param marginTop
         * @return
         */
        fun mergeBitmapInBgImageCenterHorizontal(
            backBitmap: Bitmap,
            frontBitmap: Bitmap,
            marginTop: Int
        ): Bitmap? {
            return mergeBitmap(
                backBitmap,
                frontBitmap,
                (backBitmap.width - frontBitmap.width) / 2,
                marginTop
            )
        }

        /**
         * 把两个位图覆盖合成为一个位图，以底层位图的长宽为基准
         *
         *
         * 绘制一张图片在另一张大的图背景图的上面，可以设置显示的位置
         *
         * @param backBitmap  在底部的位图
         * @param frontBitmap 盖在上面的位图
         * @return 第一个参数是图片bitmap
         * canvas.drawBitmap(frontBitmap, baseRect, frontRect, null);
         * 第二个是对图片的裁剪 也就是说你想绘制图片的哪一部分
         * If the source rectangle is not null, it specifies the subset of the bitmap to draw.
         * 第三个参数是该图片绘画的位置，就是你想把这张裁剪好的图片放在屏幕的什么位置上，比图片大，图片的等比拉伸，比图片小，就等比缩小
         * scaling/translating automatically to fill the destination rectangle
         * 第四个是画笔Paint
         */
        fun mergeBitmap(
            backBitmap: Bitmap?,
            frontBitmap: Bitmap?,
            marginLeft: Int,
            marginTop: Int
        ): Bitmap? {
            if (backBitmap == null || backBitmap.isRecycled
                || frontBitmap == null || frontBitmap.isRecycled
            ) {
                Logger.e("backBitmap=$backBitmap;frontBitmap=$frontBitmap")
                return null
            }
            val bitmap = backBitmap.copy(Bitmap.Config.ARGB_8888, true)
            val canvas = Canvas(bitmap)
            val baseRect = Rect(0, 0, frontBitmap.width, frontBitmap.height)
            val frontRect = Rect(
                marginLeft,
                marginTop,
                frontBitmap.width + marginLeft,
                frontBitmap.height + marginTop
            )
            canvas.drawBitmap(frontBitmap, baseRect, frontRect, null)
            return bitmap
        }


    }
}