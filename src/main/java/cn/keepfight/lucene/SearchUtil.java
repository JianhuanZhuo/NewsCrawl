package cn.keepfight.lucene;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 搜索工具类
 */
public class SearchUtil {

    /**
     * 查询参数解码
     */
    public static ResPage decodeQuery(QueryFormDao formDao) throws Exception{
        IndexSearcher searcher = LuceneContext.getSearcher();
        Query query;
        switch (formDao.getMethod()) {
            case "kw":
            default:
                query = searchMulti(formDao.getContent(), getField(formDao.getMatch()));
        }
        TopDocs tops = searcher.search(
                query,
                getTimeFilter(formDao.getTime()),
                formDao.getPageCapacity() * formDao.getCurrentPage(),
                getSortor(formDao.getSort()));
        ResPage resPage = new ResPage(tops.totalHits, formDao.getPageCapacity(), formDao.getCurrentPage());
        resPage.setItems(tops.scoreDocs, searcher, query);

        LuceneContext.release(searcher);
        return resPage;
    }

    /**
     * 多域通用查询
     */
    private static Query searchMulti(String content, String[] fields) throws IOException, ParseException {
        MultiFieldQueryParser parser = new MultiFieldQueryParser(LuceneContext.getVersion(), fields, new MMSegAnalyzer());
        parser.setAllowLeadingWildcard(true);
        return parser.parse(content.trim().equals("")?"?":content);
    }

    private static Query searchByField(String field, String content) throws IOException, ParseException {
        QueryParser parser = new QueryParser(LuceneContext.getVersion(), field, LuceneContext.getAnalyzer());
        return parser.parse(content);
    }

    private static String[] getField(String field){
        switch (field){
            case "title":
                return new String[]{"title"};
            case "content":
                return new String[]{"content"};
            case "cmt":
                return new String[]{"cmt"};
            case "all":
            default:
                return new String[]{"title", "content", "cmt"};
        }
    }

    /**
     * 获得排序方式
     */
    private static Sort getSortor(String sortBy){
        Sort sort;
        switch (sortBy) {
            case "hot":
                // 使用 热度 排序
                sort = new Sort(new SortField("cmt_num", SortField.INT));
                break;
            case "time":
                // 使用 日期 排序
                sort = new Sort(new SortField("pubtime", SortField.LONG, true));
                break;
            case "relative":
            default:
                // 使用 评分 排序
                sort = Sort.RELEVANCE;
        }
        return sort;
    }

    /**
     * 时间过滤器
     */
    private static Filter getTimeFilter(String timeFilter) {
        LocalDateTime currentDateTime = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
        switch (timeFilter) {
            case "week":
                currentDateTime = currentDateTime.minusWeeks(1);
                break;
            case "month":
                currentDateTime = currentDateTime.minusMonths(1);
                break;
            default:
            case "year":
                currentDateTime = currentDateTime.minusYears(1);
        }
        return NumericRangeFilter.newLongRange(
                "pubtime",
                Timestamp.valueOf(currentDateTime).getTime(),
                System.currentTimeMillis(),
                true,
                true);
    }

//    /**
//     * 查询制定 field 域中以 start 卡头，以end 节
//     * Constructs a query selecting all terms greater/equal than <code>lowerTerm</code>
//     * but less/equal than <code>upperTerm</code>.
//     */
//    public static void searchByTermRange(String field, String start, String end, int num) throws IOException {
//        IndexSearcher searcher = getSearcher();
//        // 词项查找，精确查找
//        Query query = new TermRangeQuery(field, start, end, true, true);
//        searcher.search(query, num);
//    }
//
//    /**
//     * 查数字域
//     */
//    public static void searchByIntRange(String field, int start, int end, int num) throws IOException {
//        IndexSearcher searcher = getSearcher();
//        // 词项查找，精确查找
//        Query query = NumericRangeQuery.newIntRange(field, start, end, true, true);
//        searcher.search(query, num);
//    }
//
//    /**
//     * 指定域的通配符搜索
//     */
//    public static void searchWildcard(String field, String name, int num) throws IOException {
//        IndexSearcher searcher = getSearcher();
//        // 词项查找，精确查找
//        Query query = new WildcardQuery(new Term(field, name));
//        searcher.search(query, num);
//    }
//
//
//    /**
//     * 布尔查询
//     */
//    public static void searchBoolean(int num) throws IOException {
//        IndexSearcher searcher = getSearcher();
//        // 词项查找，精确查找
//        BooleanQuery query = new BooleanQuery();
//        query.add(new BooleanClause(new TermQuery(new Term("content", "keyword1")), BooleanClause.Occur.MUST));
//        query.add(new BooleanClause(new TermQuery(new Term("title", "keyword2")), BooleanClause.Occur.MUST));
//        searcher.search(query, num);
//    }
//
//    /**
//     * 短语搜索，中文不适用
//     */
//    public static void searchPhrase(String field, String name, int num) throws IOException {
//        IndexSearcher searcher = getSearcher();
//        // 词项查找，精确查找
//        PhraseQuery query = new PhraseQuery();
//        // 设置跳数
//        query.setSlop(1);
//        query.add(new Term("content", "keyword_before"));
//        query.add(new Term("content", "keyword_after"));
//        searcher.search(query, num);
//    }
//
//    /**
//     * 短语搜索，中文不适用
//     */
//    public static void searchByParser(String field, String name, int num) throws Exception {
//        IndexSearcher searcher = getSearcher();
//        //4. 创建搜索的Query
//        QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
//        // 表示搜索 content 域中 包含 java  的文档
//        Query query = parser.parse("卖房");
//        // name 域中没有 ff，但 content 中必须有 football 的
//        query = parser.parse("-name:ff + football");
//        // ? 问号通配符
//        query = parser.parse("xx?xx");
//        // * 星号通配符
//        parser.setAllowLeadingWildcard(true);
//        query = parser.parse("*xxx");
//        // 两个关键字时默认 空格是或
//        query = parser.parse("xx yy");
//        // 修改默认为 与
//        parser.setDefaultOperator(QueryParser.Operator.AND);
//        query = parser.parse("xx yy");
//        // ID 从 1 到 3，开闭区间
//        query = parser.parse("[1 TO 3]");
//        // 完整短语
//        query = parser.parse("\"she like\"");
//        // 匹配 she 和 like 之间有一个距离的
//        query = parser.parse("\"she like\"~1");
//        // 模糊查询
//        query = parser.parse("mkae~");
//        //5. 根据 searcher 搜索并返回 TopDocs 对象
//        TopDocs tds = searcher.search(query, 10);
//        //6. 根据 TopDocs 获取 ScoreDoc 对象
//        ScoreDoc[] sds = tds.scoreDocs;
//        for (ScoreDoc sd : sds) {
//            //7. 根据 seacher 和 ScordDoc 对象获取具体的 Docment 对象
//            Document d = searcher.doc(sd.doc);
//            // 根据 Document 对象获取需要的值
//            System.out.println(d.get("filename") + "-[" + d.get("path") + "]");
//        }
//    }
//
//
//    /**
//     * 短语搜索，中文不适用
//     */
//    public static void searchBySort(String field, String name, int num, Sort sort) throws Exception {
//        IndexSearcher searcher = getSearcher();
//        // 词项查找，精确查找
//        QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
//        Query query = parser.parse("xx?xx");
//        if (sort != null) {
//            searcher.search(query, num, sort);
//        } else {
//            // 默认使用 评分 排序
//            searcher.search(query, num);
//            // 使用 ID 排序
//            searcher.search(query, num, Sort.INDEXORDER);
//            // 使用 评分 排序
//            searcher.search(query, num, Sort.RELEVANCE);
//            // 使用 文件大小 排序
//            searcher.search(query, num, new Sort(new SortField("size", SortField.INT)));
//            // 使用 日期 排序
//            searcher.search(query, num, new Sort(new SortField("date", SortField.LONG)));
//            // 使用 日期 倒序排序
//            searcher.search(query, num, new Sort(new SortField("date", SortField.LONG, true)));
//
//
//            // 使用 先日期后评分的方式 排序
//            searcher.search(query, num, new Sort(new SortField("date", SortField.LONG), SortField.FIELD_SCORE));
//        }
//    }

    private static ScoreDoc getLastDoc(int pageOffset, IndexSearcher searcher, Query query) {
        System.out.println(pageOffset);
        if (pageOffset <= 0) return null;
        try {
            TopDocs docs = searcher.search(query, pageOffset);
            return docs.scoreDocs[pageOffset - 1];
        } catch (Exception e) {
            return null;
        }
    }
}
