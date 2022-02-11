package com.zbc.androiddevelomentartdemo;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private ArrayList<String> mList = new ArrayList<>();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        mList.add("0");
        mList.add("1");
        mList.add("2");
        mList.add("3");
        mList.add("4");
        mList.add("5");
        mList.subList(0,4).clear();
        printList(mList);
        assert(mList.size() == 2);
    }

    private void printList(ArrayList<String> list) {
        for (String em : list) {
            System.out.print(" " + em);
        }
    }
}