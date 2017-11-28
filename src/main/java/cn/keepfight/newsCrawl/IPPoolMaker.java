package cn.keepfight.newsCrawl;

import cn.keepfight.dao.IPProxyDao;
import cn.keepfight.jdbc.IPProxyServices;
import cn.keepfight.server.ContentHttpAction;
import cn.keepfight.server.FetchServices;
import cn.keepfight.utils.FXUtils;
import cn.keepfight.utils.function.FunctionCheck;
import cn.keepfight.utils.lang.Pair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

public class IPPoolMaker {

    public static void pickIps() throws Exception {
        FetchServices.applyHttpclient(false, closeableHttpClient -> {
            FetchServices.fetch("http://www.xicidaili.com/wt/", inputStreamReader -> {
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
            return true;
        });
        FetchServices.applyHttpclient(false, closeableHttpClient -> {
            FetchServices.fetch("http://www.xdaili.cn/ipagent//freeip/getFreeIps", inputStreamReader -> {
                String content = FXUtils.getContent(inputStreamReader);
                System.out.println(content);
                JSONObject res = new JSONObject(content);
                JSONArray arr = res.getJSONObject("RESULT").getJSONArray("rows");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = (JSONObject) arr.get(i);
                    try {
                        IPProxyServices.insert(new IPProxyDao(obj.getString("ip"), obj.getInt("port")));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            })
                    .setParams(Arrays.asList(
                            new Pair<>("page", "1"),
                            new Pair<>("rows", "100")
                    ))
                    .setHeader(Arrays.asList(
                            new Pair<>("Pragma", "no-cache"),
                            new Pair<>("Accept", "application/json, text/plain, */*"),
                            new Pair<>("Accept-Encoding", "gzip, deflate"),
                            new Pair<>("Cache-Control", "no-cache"),
                            new Pair<>("Host", "www.xdaili.cn"),
                            new Pair<>("Referer", "http://news.qq.com/articleList/rolls/")
                    ))
                    .fetch(closeableHttpClient);
            return true;
        });
    }

    public static void main(String[] s) throws Exception {
        for (int i = 0; i < 16; i++) {
            final int thread_index = i;
            new Thread(() -> {
                System.out.println("start thread " + thread_index);
                while (true) {
                    try {
                        FetchServices.applyHttpclient(IPProxyServices::pickForCheck, closeableHttpClient -> {
                            String xx = FetchServices.fetch("http://2017.ip138.com/ic.asp",
                                    new ContentHttpAction().getProcessor())
                                    .fetch(closeableHttpClient);
                            System.out.println("proxy success! " + xx);
                            return false;
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
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
