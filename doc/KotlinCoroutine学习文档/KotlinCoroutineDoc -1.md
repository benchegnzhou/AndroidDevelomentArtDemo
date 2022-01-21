# KotlinCoroutine学习文档 1 







> **小结：**
>
> kotlin 中的thread()函数是Kotlin标准库中对java Thread的封装，调用后默认立即启动线程执行

### 1.1 传统的代码操作繁琐，而且会有回调地狱问题。

```kotlin
runOnIOThread{
    println("A")
    delay(1000){
       println("B")
       runOnUiThread{
           println("C")
       }
    }
}
```

目前给到的代码示例毕竟数量小，真实开发中随之逻辑的增加，回调的嵌套，就会出现大家经常说的"回调地狱"问题，在过往的开发中我们会使用EventBus这样的框架完成线程的调度 或者使用‘‘生产者 - 消费者’’模型来统一管理和约束异步交互。

Kotlin协程的挂起函数（suspend fun ）本质上就采用了这个异步返回值的设置思路

 

### 1.2 异常处理

传统的处理需要捕获两次异常，代码如下

```kotlin
 	fun main(){
 		try {
            val url = "http://www.baidu.com/head.webpng"
            checkUrl()
            asyncBitmap(url = url, onSuccess = ::show, onError = ::showError)
        } catch (err: Exception) {
            showError(err)
        }

    }


    private fun showError(err: Throwable) {

    }


    fun asyncBitmap(
        url: String, onSuccess: (Bitmap) -> Unit, onError: (Throwable) -> Unit
    ) {
        thread {
            try {
                download()
            } catch (err: Exception) {
                onError(err)
            }
        }
    }

    private fun download() {

    }
```

### 1.3 取消响应

异步任务如果不加任何约束，就像放出去的小狗，如果他玩够了就会自己回来。但是多数情况下我们希望他提前回来，这个时候就要去找他。

所以一部任务必须要像风筝一样，在需要的时候能够有外部主动回收。

于是我们的代码变成了这个样

```kotlin
fun main(){  
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

```

请注意取消响应中的饿响应的关键一点，需要异步任务主动配合取消，如果不配合，那么外部也就没有办法。

### 1.4 复杂分支

对于像文件分块下载这种复杂分支异步任务的case，还是很难做到统一管理的

![](http://minio.898311.xyz:8900/blogimg/16425993701594.png)











