package com.example.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class isPalindrome {

    @Test
    public void addition_isCorrect() {
        String s = "A man, a plan, a canal: Panama";
        assertEquals(true, isPalindrome(s));

        String s1 = "race a car";
        assertEquals(false, isPalindrome(s1));

        String s2 = "";
        assertEquals(true, isPalindrome(s2));

        String s3 = " ";
        assertEquals(true, isPalindrome(s3));
        assertEquals(String.valueOf(true),"true");
        String s4 = "0P";
        assertEquals(false, isPalindrome(s4));
    }

    public boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) return true;
        char[] chars = s.toCharArray();
        int start = 0;
        int end = chars.length - 1;
        char cs = chars[start];
        char ce = chars[end];
        while (start < end) {
            if (cs >= 'A' && cs <= 'Z') {
                cs += 32;
            }
            if (ce >= 'A' && ce <= 'Z') {
                ce += 32;
            }

            if (!(cs <= 'z' && cs >= 'a') && !(cs >= '0' && cs <= '9')) {
                cs = chars[++start];
                continue;
            }
            if (!(ce <= 'z' && ce >= 'a') && !(ce >= '0' && ce <= '9')) {
                ce = chars[--end];
                continue;
            }

            if (cs != ce) {
                return false;
            }
            cs = chars[++start];
            ce = chars[--end];
        }

        return true;
    }


}
