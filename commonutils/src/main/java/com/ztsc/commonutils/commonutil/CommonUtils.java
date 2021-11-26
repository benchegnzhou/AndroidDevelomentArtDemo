/**
 * Copyright (C) 2019, Xiaomi Inc. All rights reserved.
 */

package com.ztsc.commonutils.commonutil;

import android.animation.ArgbEvaluator;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

import com.ztsc.commonutils.ObjectPool;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;


/**
 * @hide
 */
public class CommonUtils {

    public static final int UNIT_SECOND = 1000;

    public static final String TAG = "miuix_anim";

    public static final ArgbEvaluator sArgbEvaluator = new ArgbEvaluator();

    private static float sTouchSlop;

    private CommonUtils() {}

    private static class OnPreDrawTask implements ViewTreeObserver.OnPreDrawListener {

        Runnable mTask;
        WeakReference<View> mView;

        OnPreDrawTask(Runnable task) {
            mTask = task;
        }

        public void start(View view) {
            ViewTreeObserver observer = view.getViewTreeObserver();
            mView = new WeakReference<>(view);
            observer.addOnPreDrawListener(this);
        }

        @Override
        public boolean onPreDraw() {
            View view = mView.get();
            if (view != null) {
                if (mTask != null) {
                    mTask.run();
                }
                view.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            mTask = null;
            return true;
        }
    }

    public static void runOnPreDraw(View view, final Runnable task) {
        if (view == null) {
            return;
        }
        new OnPreDrawTask(task).start(view);
    }

    public static int[] toIntArray(float[] floatArray) {
        int[] intArray = new int[floatArray.length];
        for (int i = 0; i < floatArray.length; i++) {
            intArray[i] = (int) floatArray[i];
        }
        return intArray;
    }

    public static String mapsToString(Map[] maps) {
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; i < maps.length; i++) {
            b.append('\n');
            b.append(i).append('.').append(mapToString(maps[i], "    "));
        }
        b.append('\n').append(']');
        return b.toString();
    }

    public static <K, V> StringBuilder mapToString(Map<K, V> map, String prefix) {
        StringBuilder b = new StringBuilder();
        b.append('{');
        if (map != null && map.size() > 0) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                b.append('\n').append(prefix);
                b.append(entry.getKey()).append('=').append(entry.getValue());
            }
            b.append('\n');
        }
        b.append('}');
        return b;
    }

    @SafeVarargs
    public static <T> T[] mergeArray(T[] l, T... r) {
        if (l == null) {
            return r;
        } else if (r == null) {
            return l;
        }
        Object newArray = Array.newInstance(l.getClass().getComponentType(), l.length + r.length);
        System.arraycopy(l, 0, newArray, 0, l.length);
        System.arraycopy(r, 0, newArray, l.length, r.length);
        return (T[]) newArray;
    }

    public static boolean hasFlags(long flags, long mask) {
        return (flags & mask) != 0;
    }

    public static int toIntValue(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Float) {
            return ((Float) value).intValue();
        } else {
            throw new IllegalArgumentException("toFloat failed, value is " + value);
        }
    }

    public static float toFloatValue(Object value) {
        if (value instanceof Integer) {
            return ((Integer) value).floatValue();
        } else if (value instanceof Float) {
            return (Float) value;
        } else {
            throw new IllegalArgumentException("toFloat failed, value is " + value);
        }
    }

    public static <T> boolean isArrayEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean inArray(T[] array, T element) {
        if (element != null && array != null && array.length > 0) {
            for (T item : array) {
                if (item.equals(element)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static float getTouchSlop(View target) {
        if (sTouchSlop == 0 && target != null) {
            sTouchSlop = ViewConfiguration.get(target.getContext()).getScaledTouchSlop();
        }
        return sTouchSlop;
    }

    public static double getDistance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private static final Class<?>[] BUILT_IN = new Class<?>[] {
            String.class,
            int.class, Integer.class, long.class, Long.class, short.class, Short.class,
            float.class, Float.class, double.class, Double.class
    };
    public static boolean isBuiltInClass(Class<?> clz) {
        return inArray(BUILT_IN, clz);
    }

    public static <T> void addTo(Collection<T> src, Collection<T> dst) {
        for (T t : src) {
            if (!dst.contains(t)) {
                dst.add(t);
            }
        }
    }

    public static String readProp(String prop) {
        InputStreamReader ir = null;
        BufferedReader input = null;
        try {
            Process process = Runtime.getRuntime().exec("getprop " + prop);
            ir = new InputStreamReader(process.getInputStream());
            input = new BufferedReader(ir);
            return input.readLine();
        } catch (IOException e) {
            Log.i(CommonUtils.TAG, "readProp failed", e);
        } finally {
            closeQuietly(input);
            closeQuietly(ir);
        }
        return "";
    }

    private static void closeQuietly(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (Exception e) {
                Log.w(CommonUtils.TAG, "close " + io + " failed", e);
            }
        }
    }

    public static <T> T getLocal(ThreadLocal<T> local, Class clz) {
        T v = local.get();
        if (v == null && clz != null) {
            v = (T) ObjectPool.acquire(clz);
            local.set(v);
        }
        return v;
    }
}
