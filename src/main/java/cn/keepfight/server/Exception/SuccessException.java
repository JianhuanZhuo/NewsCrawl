package cn.keepfight.server.Exception;

import org.apache.http.client.ClientProtocolException;

/**
 * 时间冲突
 * Created by tom on 2017/9/10.
 */
public class SuccessException extends ClientProtocolException {
    public SuccessException(){
        super("哈哈，选课成功");
    }
}
