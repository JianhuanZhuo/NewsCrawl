package cn.keepfight.services;

import cn.keepfight.dao.ArticleDao;

import java.util.ArrayList;
import java.util.List;

public class ArticlePool {

    List<ArticleDao> rawArticles = new ArrayList<>(100);

    // 获得一个文章
    ArticleDao pickArticle() {
        return null;
    }
}
