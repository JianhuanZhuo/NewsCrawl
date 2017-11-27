package cn.keepfight.server.Exception;

import org.apache.http.client.ClientProtocolException;

/**
 *
 * Created by tom on 2017/9/9.
        */
public class StatusCodeException extends ClientProtocolException {
    public StatusCodeException(){
        super("StatusCodeException");
    }
}
