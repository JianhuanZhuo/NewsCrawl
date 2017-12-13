package cn.keepfight.dao;

import java.sql.Timestamp;

/**
 * Created by 卓建欢 on 2017/11/28.
 */
public class ArticleDao {
    private Integer id;
    private String url;
    private Timestamp crawltime;
    private Timestamp pubtime;
    private Timestamp updatetime;
    private String title;
    private String pic;
    private String abs;
    private String file;
    private String cmt_id;
    private Integer cmt_num;
    private String source;
    private Long hash;

    public ArticleDao() {
    }

    public ArticleDao(String url, String title) {
        this.url = url;
        this.title = title;
        updatetime = new Timestamp(System.currentTimeMillis());
    }

    public ArticleDao(String url, Timestamp pubtime, String title) {
        this.url = url;
        this.pubtime = pubtime;
        this.title = title;
        updatetime = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getPubtime() {
        return pubtime;
    }

    public void setPubtime(Timestamp pubtime) {
        this.pubtime = pubtime;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "ArticleDao{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", crawltime=" + crawltime +
                ", pubtime=" + pubtime +
                ", updatetime=" + updatetime +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", abs='" + abs + '\'' +
                ", file='" + file + '\'' +
                ", cmt_id=" + cmt_id +
                ", cmt_num=" + cmt_num +
                ", source='" + source + '\'' +
                ", hash=" + hash +
                '}';
    }

    public String getCmt_id() {
        return cmt_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleDao that = (ArticleDao) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (crawltime != null ? !crawltime.equals(that.crawltime) : that.crawltime != null) return false;
        if (pubtime != null ? !pubtime.equals(that.pubtime) : that.pubtime != null) return false;
        if (updatetime != null ? !updatetime.equals(that.updatetime) : that.updatetime != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (pic != null ? !pic.equals(that.pic) : that.pic != null) return false;
        if (abs != null ? !abs.equals(that.abs) : that.abs != null) return false;
        if (file != null ? !file.equals(that.file) : that.file != null) return false;
        if (cmt_id != null ? !cmt_id.equals(that.cmt_id) : that.cmt_id != null) return false;
        if (cmt_num != null ? !cmt_num.equals(that.cmt_num) : that.cmt_num != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        return hash != null ? hash.equals(that.hash) : that.hash == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (crawltime != null ? crawltime.hashCode() : 0);
        result = 31 * result + (pubtime != null ? pubtime.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        result = 31 * result + (abs != null ? abs.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (cmt_id != null ? cmt_id.hashCode() : 0);
        result = 31 * result + (cmt_num != null ? cmt_num.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        return result;
    }

    public void setCmt_id(String cmt_id) {
        this.cmt_id = cmt_id;
    }

    public Long getHash() {
        return hash;
    }

    public void setHash(Long hash) {
        this.hash = hash;
    }

    public Integer getCmt_num() {
        return cmt_num;
    }

    public void setCmt_num(Integer cmt_num) {
        this.cmt_num = cmt_num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getCrawltime() {
        return crawltime;
    }

    public void setCrawltime(Timestamp crawltime) {
        this.crawltime = crawltime;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

}
