package com.example.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0123.%E4%B9%B0%E5%8D%96%E8%82%A1%E7%A5%A8%E7%9A%84%E6%9C%80%E4%BD%B3%E6%97%B6%E6%9C%BAIII.md
 */
public class MaxProfit3 {


    @Test
    public void addition_isCorrect() {
        assertEquals(6, maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4}));
        assertEquals(4, maxProfit(new int[]{1, 2, 3, 4, 5}));
        assertEquals(0, maxProfit(new int[]{7, 6, 4, 3, 1}));
    }

    public int maxProfit(int[] prices) {
        int len = prices.length;
        /**定义一个数组 dp[i][0] 和 dp[i][1]和 dp[i][2]和 dp[i][3]和 dp[i][4]
         * 其中
         * dp[i][0]  表示第i天不买入也不卖出的最大利润
         * dp[i][1]  表示第i天进行第一个买入的最大利润
         * dp[i][2]  表示第i天进行第一个卖出的最大利润
         * dp[i][3]  表示第i天进行第二个买入的最大利润
         * dp[i][4]  表示第i天进行第二个卖出的最大利润
         * dp[i][0] = 0
         * dp[i][1] = Math.max(- prices[i], dp[i-1][1])
         * dp[i][2] = Math.max(dp[i-1][1] + prices[i], dp[i-1][2])
         * dp[i][3] = Math.max(dp[i-1][2] - prices[i], dp[i-1][3])
         * dp[i][4] = Math.max(dp[i-1][3] + prices[i], dp[i-1][4])
         */
        int[][] dp = new int[len][5];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[0][2] = 0;
        dp[0][3] = -prices[0];
        dp[0][4] = 0;
        printArr(prices[0], dp, 0);
        for (int i = 1; i < len; i++) {
            dp[i][0] = 0;
            dp[i][1] = Math.max(-prices[i], dp[i - 1][1]);
            dp[i][2] = Math.max(dp[i - 1][1] + prices[i], dp[i - 1][2]);
            dp[i][3] = Math.max(dp[i - 1][2] - prices[i], dp[i - 1][3]);
            dp[i][4] = Math.max(dp[i - 1][3] + prices[i], dp[i - 1][4]);
            printArr(prices[i], dp, i);
        }
        return Math.max(Math.max(dp[len - 1][0], dp[len - 1][2]), dp[len - 1][4]);
    }

    private void printArr(int price, int[][] dp, int index) {
        System.out.println("price = " + price +
                "    dp[" + index + "][0] = " + dp[index][0] +
                "   " + "dp[" + index + "][1] = " + dp[index][1] +
                "   " + "dp[" + index + "][2] = " + dp[index][2] +
                "   " + "dp[" + index + "][3] = " + dp[index][3] +
                "   " + "dp[" + index + "][4] = " + dp[index][4]);
    }
}
