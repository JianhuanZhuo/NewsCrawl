package cn.keepfight.utils;

public abstract class NumericUtil {
    public static int getOrDefault(Integer integer) {
        if (integer == null) {
            return 0;
        } else {
            return integer;
        }
    }
}
