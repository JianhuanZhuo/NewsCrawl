package cn.keepfight.server.Exception;

import java.io.IOException;

/**
 * 时间冲突
 * Created by tom on 2017/9/10.
 */
public class TableParseException extends IOException {
    public TableParseException(){
        super("解析错误，table 内容无效");
    }
}
