package com.example.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

/**
 * LeetCode : https://leetcode.cn/problems/simplify-path/
 */
public class SimplifyPathUnix {

    @Test
    public void simplifyPath() {
        String path = "/home/";
        String result = simplifyPath(path);
        Assert.assertEquals("/home", result);
    }

    public String simplifyPath(String path) {
        if (path == null || path.length() == 0) {
            return path;
        }
        String[] files = path.split("/");
        StringBuilder simplePath = new StringBuilder();
        Stack<String> fileStack = new Stack<>();
        // 遍历路径中的每个路径。
        for (int i = 0; i < files.length; i++) {
            String file = files[i];
            if (file == "" || file == ".") {
                continue;
            } else if (file == "..") fileStack.pop();
            fileStack.add(files[i]);
        }
        return
    }

}
