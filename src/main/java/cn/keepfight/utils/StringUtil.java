package cn.keepfight.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtil {

    private static Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

    public static String unicodeToString(String str) {
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    public static String getFirstPatternMatcher(String target, Pattern patten, String defaultValue) {
        Matcher m = patten.matcher(target);
        if (m.find()) {
            return m.group(1);
        }
        return defaultValue;
    }

    public static String getFirstPatternMatcher(String target, String patten, String defaultValue) {
        return getFirstPatternMatcher(target, Pattern.compile(patten), defaultValue);
    }


    public  static String getOrDefault(String testValue){
        return testValue==null?"":testValue;
    }
}
