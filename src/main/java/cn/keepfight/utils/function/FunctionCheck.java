package cn.keepfight.utils.function;

/**
 * 带异常抛出的函数接口
 * Created by tom on 2017/7/17.
 */
@FunctionalInterface
public interface FunctionCheck<T, R> {
    R apply(T t) throws Exception;
}