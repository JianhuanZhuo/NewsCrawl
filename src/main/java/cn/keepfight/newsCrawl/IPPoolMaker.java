package cn.keepfight.newsCrawl;

import cn.keepfight.server.FetchServices;
import cn.keepfight.utils.function.FunctionCheck;
import cn.keepfight.utils.function.FunctionCheckIO;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class IPPoolMaker {

    public static void main(String[] arg) throws Exception {
        List<IPProxyDao> xx = FetchServices.applyHttpclient(true, new FunctionCheck<CloseableHttpClient, List<IPProxyDao>>() {
            @Override
            public List<IPProxyDao> apply(CloseableHttpClient closeableHttpClient) throws Exception {
                return FetchServices.fetch("http://www.xicidaili.com/wt/", new FunctionCheckIO<InputStreamReader, List<IPProxyDao>>() {
                    @Override
                    public List<IPProxyDao> apply(InputStreamReader inputStreamReader) throws IOException {
                        BufferedReader br = new BufferedReader(inputStreamReader);
                        // 获得全部内容
                        StringBuilder content = new StringBuilder();
                        String x;
                        while ((x = br.readLine()) != null) {
                            content.append(x).append("\n");
                        }

                        Document doc = Jsoup.parse(content.toString());
                        System.out.println("doc.title()"+doc.title());
                        return doc.select("#ip_list tr").stream()
                                .map(Element::text)
                                .filter(elem->!elem.startsWith("国家"))
                                .map(IPProxyDao::new)
                                .filter(IPProxyDao::notNull)
                                .collect(Collectors.toList());
                    }
                }).fetch(closeableHttpClient);
            }
        });

        xx.forEach(System.out::println);
    }
}
