package com.example.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0122.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BAII%EF%BC%88%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92%EF%BC%89.md
 */
public class MaxProfit2 {


    @Test
    public void addition_isCorrect() {
        assertEquals(7, maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        assertEquals(4, maxProfit(new int[]{1, 2, 3, 4, 5}));
        assertEquals(0, maxProfit(new int[]{7, 6, 4, 3, 1}));
    }

    public int maxProfit(int[] prices) {
        int len = prices.length;
        /**定义一个数组 dp[i][0] 和 dp[i][1]
         * 其中
         * dp[i][0]  表示第i天不持有股票所得的最大利润
         * dp[i][1]  表示第i天持有股票的最大利润
         * dp[i][0] = dp[i][1] + prices[i]
         * dp[i][1] = dp[i-1][0] - prices[i]
         */
        int[][] dp = new int[len][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        System.out.println("price = " + prices[0] + "    dp[" + 0 + "][0] = " + dp[0][0] + "   " + "dp[" + 0 + "][1] = " + dp[0][1]);
        for (int i = 1; i < len; i++) {
            dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);
            dp[i][0] = Math.max(dp[i][1] + prices[i], dp[i - 1][0]);
            System.out.println("price = " + prices[i] + "    dp[" + i + "][0] = " + dp[i][0] + "   " + "dp[" + i + "][1] = " + dp[i][1]);
        }
        return dp[len - 1][0];
    }


}
