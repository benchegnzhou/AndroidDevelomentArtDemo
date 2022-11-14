package com.example.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GenerateGetRow {


    @Test
    public void addition_isCorrect() {

        List<Integer> result = getRow(4);

            String s = "[";
            for (int j = 0; j < result.size(); j++) {
                s += result.get(j);
            }
            s += "]";
            System.out.println(s);

    }

    public List<Integer> getRow(int rowIndex) {

        List<Integer> preArr = new ArrayList<>(1);
        preArr.add(1);

        for (int i = 2; i <= rowIndex+1; i++) {
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
        }

        return preArr;
    }

}
