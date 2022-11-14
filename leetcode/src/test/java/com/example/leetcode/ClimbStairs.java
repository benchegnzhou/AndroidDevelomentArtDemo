package com.example.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/***
 *  LeetCode : https://leetcode.cn/problems/climbing-stairs/
 */
public class ClimbStairs {

    @Test
    public void addition_isCorrect() {

        int result = climbStairs(3);
        assertEquals(result, 3);
    }

    /* 使用动态规划来解决 */
    public int climbStairs(int n) {
        int dp[] = new int[Math.max(3, n + 1)];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
