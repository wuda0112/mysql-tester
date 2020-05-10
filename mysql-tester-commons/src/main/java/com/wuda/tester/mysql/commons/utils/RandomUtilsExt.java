package com.wuda.tester.mysql.commons.utils;


import org.apache.commons.lang3.RandomUtils;

/**
 * 对Apache RandomUtils的扩展.补充一些其他的随机方法.
 *
 * @author wuda
 */
public class RandomUtilsExt {

    /**
     * 在给定的数组中随机选择一个数字.
     *
     * @param candidates 候选数字,至少要有一个元素,否则抛出异常
     * @return 数组中的任意一个数字
     */
    public static int nextInt(int candidates[]) {
        if (candidates == null || candidates.length <= 0) {
            throw new IllegalArgumentException("候选数组中至少要有一个元素!");
        }
        int index = RandomUtils.nextInt(0, candidates.length);
        return candidates[index];
    }

    /**
     * 随机字符串.
     *
     * @param length 字符串长度,如果小于1,则返回空字符串(不是<code>null</code>).
     * @return 字符串
     */
    public static String randomString(int length) {
        return randomString(length, Character.MIN_CODE_POINT, Character.MAX_CODE_POINT);
    }

    public static String enRandomString(int length) {
        int[] enCodePoints = new int[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90};
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int k = 0; k < length; k++) {
            char ch = (char) nextInt(enCodePoints);
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }

    /**
     * 生成随机字符串.字符从给定的code point范围内随机获取.
     *
     * @param length                  长度
     * @param codePointStartInclusive unicode code point范围的起始值,包含
     * @param codePointEndExclusive   unicode code point范围的结束值,不包含
     * @return 随机字符串
     */
    public static String randomString(int length, int codePointStartInclusive, int codePointEndExclusive) {
        if (length <= 0) {
            return StringUtilsExt.EMPTY_STRING;
        }
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(randomChar(codePointStartInclusive, codePointEndExclusive));
        }
        return builder.toString();
    }

    /**
     * 随机生成字符串.至少包含英文字母,汉字,数字中的一种.
     *
     * @param length    字符串长度
     * @param hasLetter 是否包含英文字母
     * @param hasHan    是否包含中文汉字
     * @param hasNumber 是否包含数字
     * @return 随机字符串
     */
    public static String randomString(int length, boolean hasLetter, boolean hasHan, boolean hasNumber) {
        if (!hasLetter && !hasHan && !hasNumber) {
            throw new IllegalArgumentException("至少包含一种字符!");
        }
        if (length <= 0) {
            return StringUtilsExt.EMPTY_STRING;
        }
        return null;
    }

    /**
     * 在给定的范围内生成随机char.
     *
     * @param codePointStartInclusive unicode code point 结束值,不包含
     * @param codePointEndExclusive   unicode code point 起始值,包含
     * @return char
     */
    public static char randomChar(int codePointStartInclusive, int codePointEndExclusive) {
        return (char) RandomUtils.nextInt(codePointStartInclusive, codePointEndExclusive);
    }

    /**
     * 随机给出一个char.
     *
     * @return char
     */
    public static char randomChar() {
        return randomChar(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT);
    }

    /**
     * 常用邮箱.
     */
    static String[] emailSuffix = new String[]{"@163.com", "@126.com", "@gmail.com", "@sina.com", "@sina.cn", "@qq.com"};

    /**
     * 随机给出一个邮箱后缀,即@符号以后(包含)的部分.比如: @163.com
     *
     * @return 随机的邮箱后缀
     */
    public static String randomEmailSuffix() {
        int index = RandomUtils.nextInt(0, emailSuffix.length);
        return emailSuffix[index];
    }

}
