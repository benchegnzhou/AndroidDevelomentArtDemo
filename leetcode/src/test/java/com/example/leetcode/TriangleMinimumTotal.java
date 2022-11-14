package com.example.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TriangleMinimumTotal {

    @Test
    public void addition_isCorrect() {
        List<List<Integer>> list = new ArrayList<>();
        int arr[][] = new int[][]{{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}};
        int result = minimumTotal(covertList(list, arr));
        assertEquals(result, 11);

        int arr2[][] = new int[][]{{-10}};

        list.clear();
        int result2 = minimumTotal(covertList(list, arr2));
        assertEquals(result2, -10);

        list.clear();
        int arr3[][] = new int[][]{{2}, {3, 4}, {6, 5, 7}, {4, 1, 8, 3}};
        int result3 = minimumTotal2(covertList(list, arr3));
        assertEquals(result3, 11);

        list.clear();
        int arr4[][] = new int[][]{{-10}};
        int result4 = minimumTotal2(covertList(list, arr4));
        assertEquals(result4, -10);
    }

    private List<List<Integer>> covertList(List<List<Integer>> list, int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            List<Integer> listTemp = new ArrayList<>();
            for (int j = 0; j < arr[i].length; j++) {
                listTemp.add(arr[i][j]);
                System.out.print(arr[i][j]);
            }
            System.out.println("");
            list.add(listTemp);
        }
        return list;
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int len = triangle.size();
        if (len == 0) return 0;
        int dp[][] = new int[len][len];
        dp[0][0] = triangle.get(0).get(0);
        int min = dp[0][0];
        for (int i = 1; i < len; i++) {
            min = Integer.MAX_VALUE;
            for (int j = 0; j <= i; j++) {
                int pre = j > 0 ? dp[i - 1][j - 1] : dp[i - 1][0];
                int cur = j == i ? dp[i - 1][j - 1] : dp[i - 1][j];
                dp[i][j] = Math.min(pre, cur) + triangle.get(i).get(j);
                min = Math.min(dp[i][j], min);
            }
        }
        return min;
    }

    private Integer[][] dp;

    public int minimumTotal2(List<List<Integer>> triangle) {
        int len = triangle.size();
        dp = new Integer[len][len];
        return minimumTotal2(triangle, 0, 0, len);
    }

    public int minimumTotal2(List<List<Integer>> triangle, int i, int j, int len) {
        System.out.println("i = "+i+" , j = "+j+" ， len = "+len);
        if (i == len) {
            return 0;
        }
        if (dp[i][j] != null) {
            return dp[i][j];
        }
        return dp[i][j] = Math.min(minimumTotal2(triangle, i + 1, j, len), minimumTotal2(triangle, i + 1, j + 1, len)) + triangle.get(i).get(j);
    }


}
