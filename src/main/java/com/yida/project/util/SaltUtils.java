package com.yida.project.util;

import java.util.Random;

/**
 * 生成8位随机盐值
 */
public class SaltUtils {
    private static String str =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";

    public static String srand(int count) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(str.length());
            sb.append(str.charAt(index));
        }
        return sb.toString();
    }

}
