package cn.keepfight.utils.function;

import java.io.IOException;

/**
 * 带异常抛出的函数接口
 * Created by tom on 2017/7/17.
 */
@FunctionalInterface
public interface FunctionCheckIO<T, R> {
    R apply(T t) throws IOException;
}