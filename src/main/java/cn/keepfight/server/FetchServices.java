package cn.keepfight.server;

import cn.keepfight.dao.IPProxyDao;
import cn.keepfight.jdbc.IPProxyServices;
import cn.keepfight.utils.function.FunctionCheck;
import cn.keepfight.utils.function.FunctionCheckIO;
import cn.keepfight.utils.function.SupplierCheck;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.function.Supplier;

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
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
//指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //信任任何链接
            TrustStrategy anyTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        //设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
//      connManager.setDefaultConnectionConfig(connConfig);
//      connManager.setDefaultSocketConfig(socketConfig);
        //构建客户端
//        return HttpClientBuilder.create().setConnectionManager(connManager).build();

        if (proxy) {
            IPProxyDao dao = IPProxyServices.pickLastTime();
            System.out.println("using proxy : " + dao);
            try {
                T res = consumer.apply(HttpClients.custom()
                        .setConnectionManager(connManager)
                        .setDefaultRequestConfig(ProxyServices.getRequestConfig(dao))
                        .setRedirectStrategy(new LaxRedirectStrategy())
                        .build());
                if (dao != null) IPProxyServices.goodByID(dao.getId());
                return res;
            } catch (Exception e) {
                if (dao != null) {
                    if (dao.getFails() <= 0) {
                        IPProxyServices.deleteById(dao.getId());
                    } else {
                        IPProxyServices.badByID(dao.getId());
                    }
                }
                throw e;
            }
        } else {
            return consumer.apply(HttpClients.custom()
                    .setConnectionManager(connManager)
                    .setRedirectStrategy(new LaxRedirectStrategy())
                    .build());
        }
    }

    public static <T> T applyHttpclient(SupplierCheck<IPProxyDao> ipProxyDaoSupplier, FunctionCheck<CloseableHttpClient, T> consumer) throws Exception {
        IPProxyDao dao = ipProxyDaoSupplier.get();
        System.out.println("using proxy : " + dao);
        try {
            T res = consumer.apply(HttpClients.custom()
                    .setDefaultRequestConfig(ProxyServices.getRequestConfig(dao))
                    .setRedirectStrategy(new LaxRedirectStrategy())
                    .build());
            if (dao != null) IPProxyServices.goodByID(dao.getId());
            return res;
        } catch (Exception e) {
            if (dao != null) {
                if (dao.getFails() <= 0) {
                    IPProxyServices.deleteById(dao.getId());
                } else {
                    IPProxyServices.badByID(dao.getId());
                }
            }
            throw e;
        }
    }

    /**
     * 默认为 GET 操作
     */
    public static <T> FetchBuilder<T> fetch(String url, FunctionCheckIO<InputStreamReader, T> processor) {
        return new FetchBuilder<T>().setUrl(url).setProcessor(processor);
    }
}
