package cn.keepfight.lucene;

import cn.keepfight.dao.PageItem;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.*;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ResPage {
    private int total;
    private int pageCapacity;
    private int currentPage;
    private List<PageItem> items = new ArrayList<>(10);

    public ResPage(int total, int pageCapacity, int currentPage) {
        this.total = total;
        this.pageCapacity = pageCapacity;
        this.currentPage = currentPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCapacity() {
        return pageCapacity;
    }

    public void setPageCapacity(int pageCapacity) {
        this.pageCapacity = pageCapacity;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setItems(ScoreDoc[] docs, IndexSearcher searcher, Query query) throws IOException, InvalidTokenOffsetsException {
        items.clear();
        for (int i = pageCapacity * (currentPage - 1); i >= 0 && i < docs.length; i++) {
            ScoreDoc d = docs[i];
            Document doc = searcher.doc(d.doc);
            PageItem item = new PageItem();
            item.setPic(doc.get("pic"));
            if (item.getPic().startsWith("//mat1")) {
                item.setPic(null);
            }
            item.setUrl(doc.get("url"));
            item.setSource(doc.get("source"));
            item.setHash(Long.parseLong(doc.getFieldable("hash").stringValue()));
            item.setId(Integer.parseInt(doc.getFieldable("id").stringValue()));
            item.setCmt_num(Integer.parseInt(doc.getFieldable("cmt_num").stringValue()));
            item.setPubtime(new Timestamp(Long.parseLong(doc.getFieldable("pubtime").stringValue())));
            item.setScore(d.score);
            item.setSearch_id(d.doc);

            //评分
            QueryScorer scorer = new QueryScorer(query);
            //分段
            Formatter formatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
            Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
            Highlighter highlighter = new Highlighter(formatter, scorer);//将评分传递进去
            highlighter.setTextFragmenter(fragmenter);//将分段对象传递进去

            String content = FileUtils.readFileToString(new File("./article/" + doc.get("file")));
            try {
                long cmd = Long.parseLong(doc.getFieldable("cmt_id").stringValue());
//                System.out.println("item id : "+item.getId()+" cmd "+cmd);
                if (cmd != 0) {
                    String cmt = FileUtils.readFileToString(new File("./cmt/" + doc.get("file")));
//                    System.out.println(cmt);
                    content += "..." + cmt;
                }
            } catch (Exception e) {
                // no thing
                e.printStackTrace();
            }
            item.setAbs(highlighter.getBestFragment(LuceneContext.getAnalyzer(), "content", content));
            String title = doc.get("title");
            String hl_title = highlighter.getBestFragment(LuceneContext.getAnalyzer(), "title", doc.get("title"));
            item.setTitle(hl_title == null ? title : hl_title);

            items.add(item);
        }
    }

    public List<PageItem> getItems() {
        return items;
    }

    public void setItems(List<PageItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResPage resPage = (ResPage) o;

        if (total != resPage.total) return false;
        if (pageCapacity != resPage.pageCapacity) return false;
        if (currentPage != resPage.currentPage) return false;
        return items != null ? items.equals(resPage.items) : resPage.items == null;
    }

    @Override
    public int hashCode() {
        int result = total;
        result = 31 * result + pageCapacity;
        result = 31 * result + currentPage;
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResPage{" +
                "total=" + total +
                ", pageCapacity=" + pageCapacity +
                ", currentPage=" + currentPage +
                ", items=" + items +
                '}';
    }
}
