package com.ztsc.commonutils.threadpoolutils;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * a runnerable pool
 */
public class ThreadPoolUtil {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    public static final int MAX_SPLIT_COUNT = CPU_COUNT * 2 + 1;

    private static final int KEEP_POOL_SIZE = (CPU_COUNT < 4 ? 0 : CPU_COUNT / 2 + 1);

    private static final int KEEP_ALIVE = 30;

    private static final int MAX_SINGLE_TASK_SIZE = 4000;

    private static final ThreadPoolExecutor sCacheThread = new ThreadPoolExecutor(
            KEEP_POOL_SIZE,
            MAX_SPLIT_COUNT + 3,
            KEEP_ALIVE,
            TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(),
            getThreadFactory("CommonUtil Thread"),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    sSingleThread.execute(r);
                }
            });

    private static ThreadFactory getThreadFactory(final String factoryName) {
        return new ThreadFactory() {
            final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(@NonNull final Runnable runnable) {
                Thread thread = new Thread(runnable,
                        factoryName + "-" + this.threadNumber.getAndIncrement());
                thread.setPriority(Thread.NORM_PRIORITY);
                return thread;
            }
        };
    }

    private static final Executor sSingleThread =
            Executors.newSingleThreadExecutor(getThreadFactory("WorkThread"));

    public static void post(Runnable task) {
        sCacheThread.execute(task);
    }

    public static void getSplitCount(int animCount, int[] splitInfo) {
        int splitCount = animCount / MAX_SINGLE_TASK_SIZE;
        splitCount = Math.max(splitCount, 1);
        if (splitCount > ThreadPoolUtil.MAX_SPLIT_COUNT) {
            splitCount = ThreadPoolUtil.MAX_SPLIT_COUNT;
        }
        int singleCount = (int) Math.ceil((float) animCount / splitCount);
        splitInfo[0] = splitCount;
        splitInfo[1] = singleCount;
    }
}
