package cn.keepfight.jdbc;

import cn.keepfight.Mapper.ArticleMapper;
import cn.keepfight.dao.ArticleDao;
import cn.keepfight.utils.FXUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Timestamp;
import java.util.List;

public class ArticleServices {
    private static SqlSessionFactory factory = SqlSessionServices.getFactory();

    public static synchronized void insert(ArticleDao dao) throws Exception {
        FXUtils.getMapper(factory, ArticleMapper.class, ArticleMapper::insert, dao);
    }

    public static synchronized void stickByID(ArticleDao dao) throws Exception {
        dao.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        FXUtils.getMapper(factory, ArticleMapper.class, ArticleMapper::stickByID, dao);
    }

    public static void deleteById(int id) throws Exception {
        FXUtils.getMapper(factory, ArticleMapper.class, ArticleMapper::deleteById, id);
    }

    //    List<String> listUrl() throws Exception;
    public static List<String> listUrl() throws Exception {
        return FXUtils.getMapper(factory, ArticleMapper.class, ArticleMapper::listUrl);
    }

    public static synchronized ArticleDao pickArticle() throws Exception {
        ArticleDao res = FXUtils.getMapper(factory, ArticleMapper.class, ArticleMapper::pickArticle);
        if (res != null) updateLastByID(res.getId(), new Timestamp(System.currentTimeMillis()));
        return res;
    }


    public static  List<ArticleDao> pickArticleForIndex(int last_id) throws Exception {
        return FXUtils.getMapper(factory, ArticleMapper.class, ArticleMapper::pickArticleForIndex, last_id);
    }
    public static  List<ArticleDao> mostHotArticles() throws Exception {
        return FXUtils.getMapper(factory, ArticleMapper.class, ArticleMapper::mostHotArticles);
    }

    public static synchronized ArticleDao pickArticleForComment() throws Exception {
        ArticleDao res = FXUtils.getMapper(factory, ArticleMapper.class, ArticleMapper::pickArticleForComment);
        if (res != null) updateLastByID(res.getId(), new Timestamp(System.currentTimeMillis()));
        return res;
    }

    public static void updateLastByID(int id, Timestamp lasttime) throws Exception {
        try (SqlSession session = factory.openSession(true)) {
            session.getMapper(ArticleMapper.class).updateLastByID(id, lasttime);
        }
    }
}
