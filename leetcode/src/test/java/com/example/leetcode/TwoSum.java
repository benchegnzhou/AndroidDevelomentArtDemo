package com.example.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    @Test
    public void addition_isCorrect() {
        int[] arr = new int[]{3, 2, 4};
        int[] result = twoSumFinal(arr, 6);
        assertEquals(result[0], 1);
        assertEquals(result[1], 2);
    }

    public int[] twoSum(int[] nums, int target) {

        System.out.println(Arrays.toString(nums));
        int length = nums.length;
        if (length < 2) {
            return null;
        }
        for (int i = 0; i < length - 1; i++) {
            for (int n = i + 1; n < length; n++) {
                if (nums[i] + nums[n] == target) {
                    return new int[]{i, n};
                }
            }
        }
        return null;
    }


    public int[] twoSumFinal(int[] nums, int target) {
        Map map = new HashMap();

        int length = nums.length;
        if (length < 2) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{(int) map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

}
