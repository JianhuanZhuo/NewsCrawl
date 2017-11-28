package cn.keepfight.newsCrawl;

import cn.keepfight.dao.IPProxyDao;
import cn.keepfight.jdbc.IPProxyServices;
import cn.keepfight.server.FetchServices;
import cn.keepfight.utils.function.FunctionCheck;
import cn.keepfight.utils.function.FunctionCheckIO;
import cn.keepfight.utils.lang.Pair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleMaker {

    public static void main(String[] args) throws Exception {
        FetchServices.applyHttpclient(false, new FunctionCheck<CloseableHttpClient, Boolean>() {
            @Override
            public Boolean apply(CloseableHttpClient closeableHttpClient) throws Exception {
                FetchServices.fetch("http://roll.news.qq.com/interface/cpcroll.php?callback=rollback&site=news&mode=1&cata=&date=2017-11-28&page=1&_=" + System.currentTimeMillis(), new FunctionCheckIO<InputStreamReader, Boolean>() {
                    @Override
                    public Boolean apply(InputStreamReader inputStreamReader) throws IOException {
                        BufferedReader br = new BufferedReader(inputStreamReader);
                        // 获得全部内容
                        StringBuilder content = new StringBuilder();
                        String x;
                        while ((x = br.readLine()) != null) {
                            content.append(x).append("\n");
                        }

                        System.out.println(content);
                        return true;
                    }
                })
                        .setHeader(Arrays.asList(
                                new Pair<>("Pragma", "no-cache"),
                                new Pair<>("Accept", "*/*"),
                                new Pair<>("Accept-Encoding", "gzip, deflate"),
                                new Pair<>("Cache-Control", "no-cache"),
                                new Pair<>("Host", "roll.news.qq.com"),
                                new Pair<>("Referer", "http://news.qq.com/articleList/rolls/")
                        ))
                        .fetch(closeableHttpClient);
                return true;
            }
        });
    }
}