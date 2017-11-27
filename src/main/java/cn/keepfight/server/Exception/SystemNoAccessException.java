package cn.keepfight.server.Exception;

import org.apache.http.client.ClientProtocolException;

/**
 * 时间冲突
 * Created by tom on 2017/9/10.
 */
public class SystemNoAccessException extends ClientProtocolException {
    public SystemNoAccessException(){
        super("系统未开通选课权限");
    }
}
