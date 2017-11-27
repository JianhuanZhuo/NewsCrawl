package cn.keepfight.server;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;

/**
 * Created by 卓建欢 on 2017/11/27.
 */
public class ProxyServices {

    public static RequestConfig getRequestConfig(boolean proxy) {
        return RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();
    }
}
