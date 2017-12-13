package cn.keepfight.Mapper;

import cn.keepfight.dao.ArticleDao;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 卓建欢 on 2017/11/27.
 */
public interface ArticleMapper {

    void insert(ArticleDao dao) throws Exception;

    void stickByID(ArticleDao dao) throws Exception;

    void deleteById(int id) throws Exception;

    ArticleDao pickArticle() throws Exception;

    List<ArticleDao> pickArticleForIndex(int last_id) throws Exception;

    List<ArticleDao> mostHotArticles() throws Exception;

    ArticleDao pickArticleForComment() throws Exception;

    List<String> listUrl() throws Exception;

    void updateLastByID(@Param("id") int id,
                        @Param("updatetime") Timestamp updatetime) throws Exception;
}
