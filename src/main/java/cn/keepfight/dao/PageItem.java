package cn.keepfight.dao;

import java.sql.Timestamp;

public class PageItem {
    private Integer id;
    private String url;
    private Timestamp pubtime;
    private String title;
    private String pic;
    private String abs;
    private Integer cmt_num;
    private String source;
    private Long hash;
    private float score;
    private int search_id;


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

    public Timestamp getPubtime() {
        return pubtime;
    }

    public void setPubtime(Timestamp pubtime) {
        this.pubtime = pubtime;
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

    public Integer getCmt_num() {
        return cmt_num;
    }

    public void setCmt_num(Integer cmt_num) {
        this.cmt_num = cmt_num;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getHash() {
        return hash;
    }

    public void setHash(Long hash) {
        this.hash = hash;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getSearch_id() {
        return search_id;
    }

    public void setSearch_id(int search_id) {
        this.search_id = search_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageItem pageItem = (PageItem) o;

        if (Float.compare(pageItem.score, score) != 0) return false;
        if (search_id != pageItem.search_id) return false;
        if (id != null ? !id.equals(pageItem.id) : pageItem.id != null) return false;
        if (url != null ? !url.equals(pageItem.url) : pageItem.url != null) return false;
        if (pubtime != null ? !pubtime.equals(pageItem.pubtime) : pageItem.pubtime != null) return false;
        if (title != null ? !title.equals(pageItem.title) : pageItem.title != null) return false;
        if (pic != null ? !pic.equals(pageItem.pic) : pageItem.pic != null) return false;
        if (abs != null ? !abs.equals(pageItem.abs) : pageItem.abs != null) return false;
        if (cmt_num != null ? !cmt_num.equals(pageItem.cmt_num) : pageItem.cmt_num != null) return false;
        if (source != null ? !source.equals(pageItem.source) : pageItem.source != null) return false;
        return hash != null ? hash.equals(pageItem.hash) : pageItem.hash == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (pubtime != null ? pubtime.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        result = 31 * result + (abs != null ? abs.hashCode() : 0);
        result = 31 * result + (cmt_num != null ? cmt_num.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        result = 31 * result + (score != +0.0f ? Float.floatToIntBits(score) : 0);
        result = 31 * result + search_id;
        return result;
    }

    @Override
    public String toString() {
        return "PageItem{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", pubtime=" + pubtime +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", abs='" + abs + '\'' +
                ", cmt_num=" + cmt_num +
                ", source='" + source + '\'' +
                ", hash=" + hash +
                ", score=" + score +
                ", search_id=" + search_id +
                '}';
    }
}
