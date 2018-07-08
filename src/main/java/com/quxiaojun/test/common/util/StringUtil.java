package com.quxiaojun.test.common.util;

import java.util.UUID;

public class StringUtil {
    private StringUtil() {
        super();
    }

    /**
     * 出去null和""
     * @param src
     * @return
     */
    public static String formatNull(String src) {
        return (src == null || "null".equals(src)) ? "" : src;
    }

    /**
     * 判断字符串是否为空的正则表达式，空白字符对应的unicode编码
     */
    private static final String EMPTY_REGEX = "[\\s\\u00a0\\u2007\\u202f\\u0009-\\u000d\\u001c-\\u001f]+";

    /**
     * 验证字符串是否为空
     *
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        return input == null || input.equals("") || input.matches(EMPTY_REGEX);
    }

    public static boolean isNotEmpty(String input){
        return !isEmpty(input);
    }

    public static String getUuid() {
        return UUID.randomUUID().toString();
    }
}
