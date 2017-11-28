package cn.keepfight.server;

import cn.keepfight.dao.IPProxyDao;
import cn.keepfight.jdbc.IPProxyServices;
import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;

/**
 * Created by 卓建欢 on 2017/11/27.
 */
public class ProxyServices {

    public static RequestConfig getRequestConfig(boolean proxy) {
        RequestConfig res = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();
        if (proxy) {
            try {
                IPProxyDao dao = IPProxyServices.pickLastTime();
                System.out.println("using proxy : " + dao);
                res = RequestConfig.custom()
                        .setProxy(new HttpHost(dao.getIp(), dao.getPort()))
                        .setCookieSpec(CookieSpecs.DEFAULT)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public static RequestConfig getRequestConfig(IPProxyDao proxy) {
        if (proxy == null) {
            return RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.DEFAULT)
                    .build();
        }
        return RequestConfig.custom()
                .setProxy(new HttpHost(proxy.getIp(), proxy.getPort()))
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();
    }


}
