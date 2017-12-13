package cn.keepfight.lucene;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogByteSizeMergePolicy;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

public class LuceneContext {

    private static final Version version = Version.LUCENE_35;
    private static Analyzer analyzer;
    private static SearcherManager searcherManager;
    private static NRTManager nrtManager;
    private static IndexWriter writer;

    public static Version getVersion() {
        return version;
    }

    public static Analyzer getAnalyzer() {
        return analyzer;
    }

    public static SearcherManager getSearcherManager() {
        return searcherManager;
    }

    public static NRTManager getNrtManager() {
        return nrtManager;
    }

    static {
        analyzer = new ComplexAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(getVersion(), getAnalyzer());
        LogMergePolicy mergePolicy = new LogByteSizeMergePolicy();
        //设置segment添加文档(Document)时的合并频率
        // 值较小,建立索引的速度就较慢
        // 值较大,建立索引的速度就较快,>10适合批量建立索引
        mergePolicy.setMergeFactor(50);
        //设置segment最大合并文档(Document)数
        //值较小有利于追加索引的速度
        //值较大,适合批量建立索引和更快的搜索
        mergePolicy.setMaxMergeDocs(5000);
        indexWriterConfig.setMergePolicy(mergePolicy);
        try {
//            private static Directory directory;
//            directory = FSDirectory.open(new File("./index"));
            writer = new IndexWriter(FSDirectory.open(new File("./index")), indexWriterConfig);
//            nrtManager = new NRTManager(writer, new SearcherWarmer() {
//                @Override
//                public void warm(IndexSearcher s) throws IOException {
//                    System.out.println("index change!");
//                }
//            });
            nrtManager = new NRTManager(writer,
                    new SearcherWarmer() {
                        @Override
                        public void warm(IndexSearcher s) throws IOException {
                            System.out.println("index change!");
                        }
                    });
            searcherManager = nrtManager.getSearcherManager(true);
            NRTManagerReopenThread thread = new NRTManagerReopenThread(nrtManager, 5.0, 0.025);
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public static IndexSearcher getSearcher() {
        return searcherManager.acquire();
    }

    public static void release(IndexSearcher searcher) throws IOException {
        getSearcherManager().release(searcher);
    }

    public static void commitIndex() throws IOException {
        writer.commit();
    }

    public static void main(String[] args) {
    }
}
