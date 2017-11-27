package cn.keepfight.server.Exception;

import org.apache.http.client.ClientProtocolException;

/**
 * 时间冲突
 * Created by tom on 2017/9/10.
 */
public class SystemNoOpenException extends ClientProtocolException {
    public SystemNoOpenException(){
        super("系统未开放选课");
    }
}
