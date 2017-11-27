package cn.keepfight.server.Exception;

import java.io.IOException;

/**
 * 时间冲突
 * Created by tom on 2017/9/10.
 */
public class PatternNoExistException extends IOException {
    public PatternNoExistException(){
        super("无法匹配指定内容");
    }
}
