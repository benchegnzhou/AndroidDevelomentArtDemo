package com.example.zbclib;

import java.lang.instrument.Instrumentation;

public class ObjectSizeFetcher {
    private static Instrumentation instrumentation;

    public ObjectSizeFetcher() {
    }

    public static void premain(String var0, Instrumentation var1) {
        // 方式一：
        System.out.println("我是两个参数的 Java Agent premain");
        instrumentation = var1;
    }

    public static long getObjectSize(Object var0) {
        return instrumentation.getObjectSize(var0);
    }
}
