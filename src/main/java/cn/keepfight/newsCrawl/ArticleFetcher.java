package cn.keepfight.newsCrawl;

import cn.keepfight.dao.ArticleDao;
import cn.keepfight.jdbc.ArticleServices;
import cn.keepfight.utils.StringUtil;
import cn.keepfight.utils.lang.Pair;
import cn.keepfight.utils.simhash.Simhash;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleFetcher {

    private static Pattern cmt_idPattern = Pattern.compile("cmt_id = (\\d+)");
    private static Pattern getNum = Pattern.compile("\"oritotal\":(\\d+),");

    public static void main(String[] args) throws Exception {
//        fetch();

        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        if (!fetch()) {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private static boolean fetch() throws Exception {
        ArticleDao dao = ArticleServices.pickArticle();
        if (dao == null) {
            System.out.println("ArticleDao is null");
            return false;
        }
        Connection cont = Jsoup.connect(dao.getUrl());
        System.out.println("dao.getId() : " + dao.getId());
        System.out.println("dao.getUrl() : " + dao.getUrl());
        Element x = cont.get().body();
        Elements es = x.select(".Cnt-Main-Article-QQ");
        if (es.size() == 0) {
            if (x.select("#Main-A").size() != 0) {
                System.out.println("delete pic : " + dao.getId());
                ArticleServices.deleteById(dao.getId());
            }
        } else {
            String fileName = dao.getUrl().replace("http://", "") + ".txt";
            String content = es.text();
            FileUtils.write(new File("./article/" + fileName), content);
            dao.setFile(fileName);
            dao.setHash(new Simhash().simhash64(content));
            Matcher m = cmt_idPattern.matcher(x.data());
            if (m.find()) {
                dao.setCmt_id(m.group(1));
                Pair<Integer, StringBuilder> cmt = fetchComment(dao.getCmt_id());
                dao.setCmt_num(cmt.getKey());
                FileUtils.write(new File("./cmt/" + fileName), cmt.getValue());
            } else {
//                System.out.println(x.data());
            }
            dao.setCrawltime(new Timestamp(System.currentTimeMillis()));
            Elements img = x.select("img");
            for (Element anImg : img) {
                String imgSrc = anImg.attr("src");
                if (imgSrc.contains("inews") && !imgSrc.startsWith("//")) {
                    dao.setPic(imgSrc);
                    break;
                }
            }
            ArticleServices.stickByID(dao);
        }
        return true;
    }

    private static Pair<Integer, StringBuilder> fetchComment(String cmtID) throws IOException {
        return fetchComment(cmtID, 0);
    }

    private static Pair<Integer, StringBuilder> fetchComment(String cmtID, long last) throws IOException {
        String url = "http://coral.qq.com/article/" + cmtID + "/comment/v2?orinum=30&oriorder=o&pageflag=1&cursor=" + last + "&scorecursor=0&orirepnum=2&reporder=o&reppageflag=1&source=1";
        Connection cont = Jsoup.connect(url);
        Document respone = cont.get();
        String str = respone.text();
        JSONObject respJson = null;
        System.out.println(str);
        try {
            respJson = new JSONObject(str).getJSONObject("data");
        } catch (Exception e) {
            return new Pair<>(Integer.parseInt(StringUtil.getFirstPatternMatcher(str, getNum, "0")), new StringBuilder(""));
        }
        Pair<Integer, StringBuilder> res = new Pair<>(respJson.getInt("oritotal"), new StringBuilder());
        JSONArray arr = respJson.getJSONArray("oriCommList");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject object = (JSONObject) arr.get(i);
            res.getValue().append(object.getString("content"));
            res.getValue().append('\n');
        }
        if (respJson.getBoolean("hasnext")) {
            res.getValue().append(fetchComment(cmtID, respJson.getLong("last")).getValue());
        }
        return res;
    }
}
