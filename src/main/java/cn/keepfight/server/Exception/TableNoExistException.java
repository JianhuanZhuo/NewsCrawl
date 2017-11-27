package cn.keepfight.server.Exception;

import java.io.IOException;

/**
 * 时间冲突
 * Created by tom on 2017/9/10.
 */
public class TableNoExistException extends IOException {
    public TableNoExistException(){
        super("解析错误，不存在 table 内容");
    }
}
