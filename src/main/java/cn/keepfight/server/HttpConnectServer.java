package cn.keepfight.server;

import cn.keepfight.utils.function.ConsumerCheck;
import cn.keepfight.utils.function.FunctionCheck;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.IOException;
import java.util.function.Function;

/**
 * http 连接服务
 * <p>
 * <ol>
 * <li>登录</li>
 * <li>点击任意以刷新</li>
 * <li>查看全部课程</li>
 * <li>查看已选课程</li>
 * <li>删除已选课程</li>
 * <li>提交选课</li>
 * </ol>
 * Created by tom on 2017/9/8.
 */
public class HttpConnectServer {
    private static HttpConnectServer instance = new HttpConnectServer();

    public static HttpConnectServer getInstance() {
        return instance;
    }


    private CloseableHttpClient httpclient;

    private HttpConnectServer() {
        // 默认 cookie 策略
        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();
        httpclient = HttpClients.custom()
                .setDefaultRequestConfig(globalConfig)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
    }

    /**
     * 申请使用
     */
    public synchronized <T> T applyHttpclient(FunctionCheck<CloseableHttpClient, T> consumer) throws Exception{
        return consumer.apply(httpclient);
    }

    /**
     * 关闭
     */
    void close() throws IOException {
        httpclient.close();
    }
}
