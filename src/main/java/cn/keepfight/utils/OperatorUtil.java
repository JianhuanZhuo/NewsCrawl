package cn.keepfight.utils;

public abstract class OperatorUtil {
    public  static<T> T getOrDefault(T testValue,T defaultValue){
        return testValue==null?defaultValue:testValue;
    }
}
