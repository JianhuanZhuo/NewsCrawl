package cn.keepfight.server;

import cn.keepfight.utils.function.FunctionCheck;
import cn.keepfight.utils.function.FunctionCheckIO;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.InputStreamReader;

public abstract class FetchServices {

    // 默认 cookie 策略
    private static RequestConfig globalConfig = RequestConfig.custom()
            .setCookieSpec(CookieSpecs.DEFAULT)
            .build();

    private static CloseableHttpClient globalClient = HttpClients.custom()
            .setDefaultRequestConfig(globalConfig)
            .setRedirectStrategy(new LaxRedirectStrategy())
            .build();

    enum SUPPORT_METHODS {
        POST, GET
    }

    /**
     * 默认 cookie 策略
     */
    public static RequestConfig getGlobalConfig() {
        return globalConfig;
    }

    /**
     * 申请使用
     */
    public static synchronized <T> T applyHttpclientGlobal(FunctionCheck<CloseableHttpClient, T> consumer) throws Exception {
        return consumer.apply(globalClient);
    }

    public static <T> T applyHttpclient(boolean proxy, FunctionCheck<CloseableHttpClient, T> consumer) throws Exception {
        return consumer.apply(HttpClients.custom()
                .setDefaultRequestConfig(ProxyServices.getRequestConfig(proxy))
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build());
    }

    /**
     * 默认为 GET 操作
     */
    public static <T> FetchBuilder<T> fetch(String url, FunctionCheckIO<InputStreamReader, T> processor) {
        return new FetchBuilder<T>().setUrl(url).setProcessor(processor);
    }
}
