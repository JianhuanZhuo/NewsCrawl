package cn.keepfight.server.Exception;

import org.apache.http.client.ClientProtocolException;

/**
 * 时间冲突
 * Created by tom on 2017/9/10.
 */
public class SessionFailException extends ClientProtocolException {
    public SessionFailException(){
        super("异常跳转");
    }
}
