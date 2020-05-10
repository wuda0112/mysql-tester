package com.wuda.tester.mysql.commons.utils;

/**
 * string util.
 *
 * @author wuda
 */
public class StringUtilsExt {

    /**
     * 空字符串.
     */
    public static String EMPTY_STRING = "";

    /**
     * 大小写枚举.
     */
    public static enum CaseEnum {
        upper, lower;
    }

    /**
     * 将第一个字符大写.
     *
     * @param str 字符串
     * @return 新的字符串
     */
    public static String firstCharToUpperCase(String str) {
        return changeCaseAt(str, 0, CaseEnum.upper);
    }

    /**
     * 将第一个字符小写.
     *
     * @param str 字符串
     * @return 新的字符串
     */
    public static String firstCharToLowerCase(String str) {
        return changeCaseAt(str, 0, CaseEnum.lower);
    }

    /**
     * 改变指定位置字符的大小写.
     *
     * @param str          string
     * @param index        位置
     * @param lowerOrUpper lower or upper
     * @return new string
     */
    public static String changeCaseAt(String str, int index, CaseEnum lowerOrUpper) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        if (index >= str.length()) {
            return str;
        }
        char oldChar = str.charAt(index);
        char newChar = 0x00;
        switch (lowerOrUpper) {
            case upper:
                newChar = Character.toUpperCase(oldChar);
                break;
            case lower:
                newChar = Character.toLowerCase(oldChar);
                break;
        }
        if (newChar == oldChar) {
            return str;
        }
        char[] copy = str.toCharArray();
        copy[index] = newChar;
        return new String(copy);
    }

    /**
     * 为字符串添加给定的前缀.
     *
     * @param str    原始字符串
     * @param prefix 前缀
     * @return 前缀与原始字符串组成的新字符串
     */
    public static String addPrefix(String str, String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return str;
        }
        if (str == null || str.isEmpty()) {
            return prefix;
        }
        char[] chars = new char[str.length() + prefix.length()];
        for (int i = 0; i < prefix.length(); i++) {
            chars[i] = prefix.charAt(i);
        }
        int k = prefix.length();
        for (int j = 0; j < str.length(); j++) {
            chars[k++] = str.charAt(j);
        }
        return new String(chars);
    }

    /**
     * 为字符串添加给定的后缀.
     *
     * @param str    原始字符串
     * @param suffix 后缀
     * @return 原始字符串与后缀组成的新字符串
     */
    public static String addSuffix(String str, String suffix) {
        return addPrefix(suffix, str);
    }

    /**
     * 去除掉最后一个char.
     *
     * @param charSequence char sequence
     * @return 去除最后一个字符后的字符串
     */
    public static String removeLastChar(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        } else if (charSequence.length() <= 1) {
            return EMPTY_STRING;
        } else {
            return charSequence.subSequence(0, lastCharIndex(charSequence)).toString();
        }
    }

    /**
     * 最后一个字符的下标.
     *
     * @param charSequence char sequence
     * @return 最后一个字符的下标
     */
    public static int lastCharIndex(CharSequence charSequence) {
        return charSequence.length() - 1;
    }
}
