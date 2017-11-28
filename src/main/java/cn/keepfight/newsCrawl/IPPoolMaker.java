package cn.keepfight.newsCrawl;

import cn.keepfight.dao.IPProxyDao;
import cn.keepfight.jdbc.IPProxyServices;
import cn.keepfight.server.ContentHttpAction;
import cn.keepfight.server.FetchServices;
import cn.keepfight.utils.FXUtils;
import cn.keepfight.utils.function.FunctionCheck;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.net.InetAddress;

public class IPPoolMaker {

    public static void pickIps() throws Exception {
        FetchServices.applyHttpclient(true, closeableHttpClient -> {
            for (int i = 1; i <10; i++) {
                FetchServices.fetch("http://www.xicidaili.com/wt/"+i, inputStreamReader -> {
                    String content = FXUtils.getContent(inputStreamReader);
                    Jsoup.parse(content).select("#ip_list tr").stream()
                            .map(Element::text)
                            .filter(elem -> !elem.startsWith("国家"))
                            .map(IPProxyDao::new)
                            .filter(IPProxyDao::notNull)
                            .forEach(dao -> {
                                try {
                                    IPProxyServices.insert(dao);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                    return true;
                }).fetch(closeableHttpClient);
            }
            return true;
        });
    }

    public static void validateIP() throws Exception {
        for (int i = 0; i < 16; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        FetchServices.applyHttpclient(true, closeableHttpClient -> {
                            String xx = FetchServices.fetch("http://2017.ip138.com/ic.asp",
                                    new ContentHttpAction().getProcessor())
                                    .fetch(closeableHttpClient);
//                            System.out.println("proxy success! " + xx);
                            return false;
                        });
                        Thread.sleep(1000);
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    public static void main4(String[] arg) throws Exception {
        for (int i = 0; i < 8; i++) {
            new ConnectTest().start();
        }
    }

    static class ConnectTest extends Thread {
        @Override
        public void run() {
            while (true) {
                IPProxyDao dao = null;
                try {
                    dao = IPProxyServices.pickLastTime();
                    boolean connectionStatus = InetAddress.getByName(dao.getIp()).isReachable(1000);
                    System.out.println("res : " + connectionStatus + "\n" + dao);
                    if (connectionStatus) {
                        IPProxyServices.goodByID(dao.getId());
                    } else {
                        IPProxyServices.badByID(dao.getId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
