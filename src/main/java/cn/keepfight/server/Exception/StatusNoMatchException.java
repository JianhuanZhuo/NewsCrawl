package cn.keepfight.server.Exception;

import org.apache.http.client.ClientProtocolException;

/**
 *
 * Created by tom on 2017/9/9.
        */
public class StatusNoMatchException extends ClientProtocolException {
    public StatusNoMatchException(){
        super("状态未匹配错误！");
    }
}
