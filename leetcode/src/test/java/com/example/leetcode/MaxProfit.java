package com.example.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class MaxProfit {


    @Test
    public void addition_isCorrect() {
        int result = maxProfit2(new int[]{7, 1, 5, 3, 6, 4});
        assertEquals(result, 5);
        int result2 = maxProfit2(new int[]{7, 6, 4, 3, 1});
        assertEquals(result2, 0);

    }

    /* 暴力法 */
    public int maxProfit(int[] prices) {
        int len = prices.length;

        int maxP = 0;
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                maxP = Math.max(maxP, prices[j] - prices[i]);
            }
        }
        return maxP;
    }

    /* 一次遍历法 */
    public int maxProfit2(int[] prices) {
        int len = prices.length;
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i < len; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            }
            if (prices[i] - minPrice > maxProfit) {
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }

}
