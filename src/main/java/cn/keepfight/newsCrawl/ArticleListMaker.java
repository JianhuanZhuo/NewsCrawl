package cn.keepfight.newsCrawl;

import cn.keepfight.dao.ArticleDao;
import cn.keepfight.jdbc.ArticleServices;
import cn.keepfight.server.FetchServices;
import cn.keepfight.utils.function.FunctionCheck;
import cn.keepfight.utils.function.FunctionCheckIO;
import cn.keepfight.utils.lang.Pair;
import org.apache.commons.io.FileUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleListMaker {

    private static LocalDate x = LocalDate.of(2017, 2, 3);

    private synchronized static String getDateStr() {
        String res = x.format(formatter);
        x = x.plusDays(1);
        if (x.isAfter(LocalDate.now())) {
            return null;
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
//        for (int i = 0; i < 16; i++) {
//            new Thread(() -> {
//                while (true) {
//                    String date = getDateStr();
//                    if (date == null) {
//                        break;
//                    }
//                    try {
//                        listMaker(date);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }


//        for (int i = 1; i < 11; i++) {
//            final int th = i;
//            new Thread(() -> {
//                for (int j = th * 10; j < th * 10 + 10; j++) {
//                    try {
//                        listMaker_xinlang(j);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }


        for (int j = 30; j < 1000; j++) {
            try {
                listMaker_xinlang(j);
                System.out.println("j:" + j);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        listMaker_xinlang(1);
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("Y-MM-dd");
    private static Pattern countPattern = Pattern.compile("\"count\":(\\d+)");

    public static void listMaker(String date) throws IOException {
        String urlLink = "http://roll.news.qq.com/interface/cpcroll.php?callback=rollback&site=news&mode=2&cata=&date=" + date + "&page=" + 1 + "&_=" + System.currentTimeMillis();
        Connection cont = Jsoup.connect(urlLink);
        cont.header("Referer", "http://news.qq.com/articleList/rolls/");
        Document respone = cont.get();
        String str = respone.text();
        processList(str);
        Matcher m = countPattern.matcher(str);
        if (m.find()) {
            int count = Integer.parseInt(m.group(1));
            for (int i = 0; i < count; i++) {
                try {
                    String urlLinkX = "http://roll.news.qq.com/interface/cpcroll.php?callback=rollback&site=news&mode=2&cata=&date=" + date + "&page=" + i + "&_=" + System.currentTimeMillis();
                    Connection cont2 = Jsoup.connect(urlLinkX);
                    cont2.header("Referer", "http://news.qq.com/articleList/rolls/");
                    Document respone2 = cont2.get();
                    String str2 = respone2.text();
                    processList(str2);
                } catch (Exception e) {
                    // nothing
                }
            }
        }
    }

    public static void listMaker_xinlang(int p) throws IOException {
        String urlLink = "http://interface.sina.cn/wap_api/news_roll.d.html?show_num=30&page=" + p;
        Connection cont = Jsoup.connect(urlLink);
        Document respone = cont.get();
        String str = respone.text();
        try {
            processList_xinlang(str);

        } catch (Exception e) {
            System.out.println("str:" + str);
            throw e;
        }
    }

    private static void processList_xinlang(String str) {
        JSONArray arr = new JSONObject(str).getJSONObject("result").getJSONObject("data").getJSONArray("list");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject object = (JSONObject) arr.get(i);
            String title = object.getString("title");
            String url = object.getString("URL");
            ArticleDao dao = new ArticleDao(url, title);
            dao.setSource("xl");
            dao.setCmt_id(object.getString("commentid"));
            dao.setCmt_num(object.getInt("comment"));
            if (object.getJSONArray("allPics").length() > 0) {
                dao.setPic(object.getJSONArray("allPics").getJSONObject(0).getString("imgurl"));
            }
            dao.setUpdatetime(new Timestamp(System.currentTimeMillis()));
            try {
                ArticleServices.insert(dao);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(dao);
        }
    }

    public static void listMaker_wangyi(int p) throws IOException {
        String urlLink = "https://3g.163.com/touch/reconstruct/article/list/BBM54PGAwangning/" + p * 2 + "0-20.html";
        Connection cont = Jsoup.connect(urlLink);
        cont.referrer("http://3g.163.com/touch/news/subchannel/history?dataversion=A&uversion=A&version=v_standard");
        Document respone = cont.get();
        String str = respone.text();
        processList_wangyi(str);
    }

    private static void processList_wangyi(String str) {
        try {
            str = str.substring(9);
            str = str.substring(0, str.length() - 1);
        } catch (Exception e) {
            System.out.println("str : " + str);
            throw e;
        }
        JSONArray arr = new JSONObject(str).getJSONArray("BBM54PGAwangning");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject object = (JSONObject) arr.get(i);
            if (object.has("hasImg")) {
                String title = object.getString("title");
                String url = object.getString("url");
                String time = object.getString("ptime");
                ArticleDao dao = new ArticleDao(url, Timestamp.valueOf(time), title);
                dao.setSource("wy");
                dao.setCmt_id(object.getString("docid"));
                dao.setCmt_num(object.getInt("commentCount"));
                dao.setPic(object.getString("imgsrc"));
                dao.setUpdatetime(new Timestamp(System.currentTimeMillis()));
                try {
                    ArticleServices.insert(dao);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(dao);
            }
        }
    }

    private static void processList(String str) {
        str = str.substring(9);
        str = str.substring(0, str.length() - 1);
        JSONArray arr = new JSONObject(str).getJSONObject("data").getJSONArray("article_info");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject object = (JSONObject) arr.get(i);
            String title = object.getString("title");
            String url = object.getString("url");
            String time = object.getString("time");
            ArticleDao dao = new ArticleDao(url, Timestamp.valueOf(time), title);
            try {
                ArticleServices.insert(dao);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(dao);
        }
    }
}
