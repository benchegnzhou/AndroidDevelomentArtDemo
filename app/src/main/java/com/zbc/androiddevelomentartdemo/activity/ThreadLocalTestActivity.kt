package com.zbc.androiddevelomentartdemo.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zbc.androiddevelomentartdemo.R
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.Exception
import kotlin.concurrent.thread

class ThreadLocalTestActivity : AppCompatActivity() {
    var threadLocal: ThreadLocal<String> = ThreadLocal<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_local_test)
        threadLocal.set("this is first string!")
        threadLocal.set("this is two string!")
        threadLocal.set("this is three string!")
        threadLocal.set("this is four string!")

        thread {
            for (i in 1..4) {
                Log.e("----", "${threadLocal.get()}");
            }
        }
        try {
            val url = "http://www.baidu.com/head.webpng"
            checkUrl()
            asyncBitmapCancellable(url = url, onSuccess = ::show, onError = ::showError)
        } catch (err: Exception) {
            showError(err)
        }

    }


    private fun showError(err: Throwable) {

    }


    fun asyncBitmapCancellable(
        url: String, onSuccess: (Bitmap) -> Unit, onError: (Throwable) -> Unit
    ) =
        thread {
            try {
                downloadCancellable(url)
            } catch (err: Exception) {
                onError(err)
            }
        }


    private fun downloadCancellable(url: String): Bitmap {
        return getAsStream(url).use { inputStream ->
            val bos = ByteArrayOutputStream()
            val buffer = ByteArray(1024 * 8)
            while (true) {
                ...
                if (Thread.interrupted()) {
                    throw InterruptedException("Task is cancelled")
                }
            }
        }
    }

    private fun getAsStream(url: String): InputStream {

    }

}

