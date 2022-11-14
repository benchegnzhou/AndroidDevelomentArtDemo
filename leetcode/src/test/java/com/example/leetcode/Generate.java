package com.example.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/* 118. 杨辉三角 https://leetcode.cn/problems/pascals-triangle/ */
public class Generate {


    @Test
    public void addition_isCorrect() {

        List<List<Integer>> result = generate(4);
        for (int i = 0; i < result.size(); i++) {
            String s = "[";
            for (int j = 0; j < result.get(i).size(); j++) {
                s += result.get(i).get(j);
            }
            s += "]";
            System.out.println(s);
        }
    }

    public List<List<Integer>> generate(int numRows) {
        List list = new ArrayList<List<Integer>>();

        List<Integer> preArr = new ArrayList<>(1);
        preArr.add(1);
        list.add(preArr);

        for (int i = 2; i <= numRows; i++) {
            List<Integer> dp = new ArrayList(i);
            for (int j = 0; j < i ; j++) {
                if (j < 1) {
                    dp.add(preArr.get(j));
                } else if (j >= i - 1) {
                    dp.add(preArr.get(j - 1));
                } else {
                    dp.add(preArr.get(j - 1) + preArr.get(j));
                }
            }
            preArr = dp;
            list.add(dp);
        }

        return list;
    }

}
