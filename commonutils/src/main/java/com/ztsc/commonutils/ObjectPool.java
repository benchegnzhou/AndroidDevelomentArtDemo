package com.ztsc.commonutils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.ztsc.commonutils.commonutil.CommonUtils;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ObjectPool {

    public interface IPoolObject {
        void clear();
    }

    private static final int MAX_POOL_SIZE = 10;
    private static final long DELAY = 5000;
    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());

    private static class Cache {
        /**
         * 参考： https://www.cnblogs.com/yangzhenlong/p/8359875.html
         * <p>
         * 一个基于链接节点的无界线程安全队列。此队列按照 FIFO（先进先出）原则对元素进行排序。队列的头部 是队列中时间最长的元素。队列的尾部 是队列中时间最短的元素。
         * 新的元素插入到队列的尾部，队列获取操作从队列头部获得元素。当多个线程共享访问一个公共 collection 时，ConcurrentLinkedQueue 是一个恰当的选择。此队列不允许使用 null 元素。
         * <p>
         * </p>
         * offer(E e)
         * 将指定元素插入此队列的尾部。
         * <p>
         * poll()
         * 获取并移除此队列的头，如果此队列为空，则返回 null。
         * peek()
         * 获取但不移除此队列的头；如果此队列为空，则返回 null
         * remove(Object o)
         * 从队列中移除指定元素的单个实例（如果存在）
         * size()
         * 返回此队列中的元素数量
         * 如果此队列包含的元素数大于 Integer.MAX_VALUE，则返回 Integer.MAX_VALUE。
         * 需要小心的是，与大多数 collection 不同，此方法不是 一个固定时间操作。由于这些队列的异步特性，确定当前的元素数需要进行一次花费 O(n) 时间的遍历。
         * 所以在需要判断队列是否为空时，尽量不要用 queue.size()>0，而是用 !queue.isEmpty()
         * ------------------------------------------------------------------------------------------------------
         * <p>
         * 数量为10000;级别的数据
         * 使用size耗时：757ms
         * 使用isEmpty耗时：210
         * </p>
         * contains(Object o)
         * 如果此队列包含指定元素，则返回 true
         * <p>
         * iterator()
         * 返回在此队列元素上以恰当顺序进行迭代的迭代器
         * add(E e)
         * 将指定元素插入此队列的尾部。
         * toArray()
         * 返回以恰当顺序包含此队列所有元素的数组。
         * toArray(T[] a)
         * 返回以恰当顺序包含此队列所有元素的数组；返回数组的运行时类型是指定数组的运行时类型。
         */
        final ConcurrentLinkedQueue<Object> pool = new ConcurrentLinkedQueue<>();
        final ConcurrentHashMap<Object, Boolean> mCacheRecord = new ConcurrentHashMap<>();
        final Runnable shrinkTask = new Runnable() {
            @Override
            public void run() {
                shrink();
            }
        };

        <T> T acquireObject(Class<T> clz, Object... args) {
            Object obj = pool.poll();
            if (obj != null) {
                mCacheRecord.remove(obj);
            } else if (clz != null) {
                obj = createObject(clz, args);
            }
            return (T) obj;
        }

        void releaseObject(Object obj) {
            if (mCacheRecord.putIfAbsent(obj, true) != null) {
                return;
            }
            pool.add(obj);
            sMainHandler.removeCallbacks(shrinkTask);
            if (pool.size() > MAX_POOL_SIZE) {
                sMainHandler.postDelayed(shrinkTask, DELAY);
            }
        }

        void shrink() {
            while (pool.size() > MAX_POOL_SIZE) {
                Object obj = pool.poll();
                if (obj == null) {
                    break;
                }
                mCacheRecord.remove(obj);
            }
        }
    }

    private static final ConcurrentHashMap<Class<?>, Cache> sCacheMap
            = new ConcurrentHashMap<>();

    private ObjectPool() {
    }

    public static <T> T acquire(final Class<T> clz, Object... args) {
        Cache cache = getObjectCache(clz, true);
        return cache.acquireObject(clz, args);
    }

    public static void release(Object obj) {
        if (obj == null) {
            return;
        }
        Class<?> clz = obj.getClass();
        if (obj instanceof IPoolObject) {
            ((IPoolObject) obj).clear();
        } else if (obj instanceof Collection) {
            ((Collection) obj).clear();
        } else if (obj instanceof Map) {
            ((Map) obj).clear();
        }
        Cache cache = getObjectCache(clz, false);
        if (cache != null) {
            cache.releaseObject(obj);
        }
    }

    private static Cache getObjectCache(Class<?> clz, boolean create) {
        Cache cache = sCacheMap.get(clz);
        if (cache == null && create) {
            cache = new Cache();
            //如果所指定的 key 已经在 HashMap 中存在，返回和这个 key 值对应的 value, 如果所指定的 key 不在 HashMap 中存在，则返回 null。
            Cache prev = sCacheMap.putIfAbsent(clz, cache);
            cache = prev != null ? prev : cache;
        }
        return cache;
    }

    /**
     * 通过反射创建对象
     *
     * @param clz  被创建对象的class
     * @param args 构造函数的长度
     * @return 创建好的对象
     */
    private static Object createObject(Class<?> clz, Object... args) {
        try {
            Constructor<?>[] ctrs = clz.getDeclaredConstructors();
            for (Constructor<?> ctr : ctrs) {
                if (ctr.getParameterTypes().length != args.length) {
                    continue;
                }
                ctr.setAccessible(true);
                return ctr.newInstance(args);
            }
        } catch (Exception e) {
            Log.w(CommonUtils.TAG, "ObjectPool.createObject failed, clz = " + clz, e);
        }
        return null;
    }
}