package com.lxp.utils;

import java.util.Random;

/**
 * Created by Li Xiaopeng on 2017/7/4.
 */

public class NumberUtils {
    /**
     * 生成指定长度的随即串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
