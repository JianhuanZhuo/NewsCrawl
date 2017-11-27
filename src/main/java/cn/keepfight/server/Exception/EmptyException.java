package cn.keepfight.server.Exception;

import org.apache.http.client.ClientProtocolException;

/**
 * 时间冲突
 * Created by tom on 2017/9/10.
 */
public class EmptyException extends ClientProtocolException {
    public EmptyException(){
        super("无选择课程");
    }
}
