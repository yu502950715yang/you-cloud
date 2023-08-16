package com.you.common.utils;

public class StrUtil {
    public static String convertCamelToUnderscore(String camelString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelString.length(); i++) {
            char currentChar = camelString.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                result.append('_').append(Character.toLowerCase(currentChar));
            } else {
                result.append(currentChar);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String camelString = "camelCaseString";
        String underscoreString = convertCamelToUnderscore(camelString);
        System.out.println(underscoreString);  // 输出：camel_case_string
    }
}
