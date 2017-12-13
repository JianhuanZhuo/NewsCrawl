package cn.keepfight.lucene;

import cn.keepfight.dao.ArticleDao;
import cn.keepfight.jdbc.ArticleServices;
import cn.keepfight.utils.StringUtil;
import cn.keepfight.utils.simhash.Simhash;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class IndexUtils {


    public static void main(String[] args) throws IOException {
        index();
    }

    /**
     * 建立索引
     */
    public static void index() throws IOException {
        LuceneContext.getNrtManager().deleteAll();
        int last_id = 0;
        int indexed_count = 0;
        long st = System.currentTimeMillis();
        while (last_id != -1) {
            try {
                st = log(st, indexed_count, "start");
                List<ArticleDao> daoList = ArticleServices.pickArticleForIndex(last_id);
                st = log(st, indexed_count, "get");
                if (daoList == null || daoList.isEmpty()) {
                    System.out.println("last_id : " + last_id);
                    System.exit(0);
                }
                System.out.println("index indexed_count : " + indexed_count);
                System.out.println("index last_id : " + last_id);
                last_id = daoList.stream().mapToInt(ArticleDao::getId).max().orElse(-1);
                st = log(st, indexed_count, "stream");
                daoList.parallelStream().forEach(dao -> {
                    try {
                        // 3. 创建 Document 对象，也就是每个文档对应一个 Document
                        Document doc = new Document();
                        String content = FileUtils.readFileToString(new File("./article/" + dao.getFile()));
                        doc.add(new NumericField("id", Field.Store.YES, true).setIntValue(dao.getId()));
                        doc.add(new NumericField("pubtime", Field.Store.YES, true).setLongValue(dao.getPubtime().getTime()));
                        doc.add(new Field("content", content, Field.Store.NO, Field.Index.ANALYZED));
                        doc.add(new Field("title", dao.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
                        doc.add(new Field("pic", StringUtil.getOrDefault(dao.getPic()), Field.Store.YES, Field.Index.NO));
                        doc.add(new Field("url", dao.getUrl(), Field.Store.YES, Field.Index.NO));
                        doc.add(new Field("file", dao.getFile(), Field.Store.YES, Field.Index.NO));
                        doc.add(new Field("source", dao.getSource(), Field.Store.YES, Field.Index.NO));
                        if (dao.getHash() == null) {
                            doc.add(new NumericField("hash", Field.Store.YES, true).setLongValue(new Simhash().simhash64(content)));
                        } else {
                            doc.add(new NumericField("hash", Field.Store.YES, true).setLongValue(dao.getHash()));
                        }
                        if (dao.getCmt_id() == null) {
                            doc.add(new Field("cmt", "", Field.Store.NO, Field.Index.ANALYZED));
                            doc.add(new NumericField("cmt_num", Field.Store.YES, true).setIntValue(0));
                            doc.add(new NumericField("cmt_id", Field.Store.YES, true).setIntValue(0));
                        } else {
                            doc.add(new Field("cmt", FileUtils.readFileToString(new File("./cmt/" + dao.getFile())), Field.Store.NO, Field.Index.ANALYZED));
                            doc.add(new NumericField("cmt_num", Field.Store.YES, true).setLongValue(dao.getCmt_num()));
                            doc.add(new Field("cmt_id", dao.getCmt_id(), Field.Store.YES, Field.Index.NO));
                        }
                        LuceneContext.getNrtManager().addDocument(doc);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                st = log(st, indexed_count, "commit");
                LuceneContext.commitIndex();
                st = log(st, indexed_count, "end \n");
                indexed_count += daoList.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static long log(long stamp, int x, String tip) {
        x = x % 1000;
        if (x == 0 || x == 800) {
            long c = System.currentTimeMillis();
            long interval = c - stamp;
            System.out.print(interval + " - " + tip + " || ");
            return c;
        }
        return stamp;
    }
}
