package cn.keepfight.dao;

import java.awt.*;
import java.sql.Timestamp;

/**
 * Created by 卓建欢 on 2017/11/28.
 */
public class ArtcleDao {
    private Integer id;
    private String url;
    private Timestamp crawletime;
    private Timestamp pubtime;
    private Timestamp updatetime;
    private String title;
    private String pic;
    private String abs;
    private String file;

    public ArtcleDao(){
    }

    public ArtcleDao(String url, Timestamp pubtime, String title) {
        this.url = url;
        this.pubtime = pubtime;
        this.title = title;
        crawletime = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getPubtime() {
        return pubtime;
    }

    public void setPubtime(Timestamp pubtime) {
        this.pubtime = pubtime;
    }

    @Override
    public String toString() {
        return "ArtcleDao{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", crawletime=" + crawletime +
                ", pubtime=" + pubtime +
                ", updatetime=" + updatetime +
                ", title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                ", abs='" + abs + '\'' +
                ", file='" + file + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtcleDao artcleDao = (ArtcleDao) o;

        if (id != null ? !id.equals(artcleDao.id) : artcleDao.id != null) return false;
        if (url != null ? !url.equals(artcleDao.url) : artcleDao.url != null) return false;
        if (crawletime != null ? !crawletime.equals(artcleDao.crawletime) : artcleDao.crawletime != null) return false;
        if (pubtime != null ? !pubtime.equals(artcleDao.pubtime) : artcleDao.pubtime != null) return false;
        if (updatetime != null ? !updatetime.equals(artcleDao.updatetime) : artcleDao.updatetime != null) return false;
        if (title != null ? !title.equals(artcleDao.title) : artcleDao.title != null) return false;
        if (pic != null ? !pic.equals(artcleDao.pic) : artcleDao.pic != null) return false;
        if (abs != null ? !abs.equals(artcleDao.abs) : artcleDao.abs != null) return false;
        return file != null ? file.equals(artcleDao.file) : artcleDao.file == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (crawletime != null ? crawletime.hashCode() : 0);
        result = 31 * result + (pubtime != null ? pubtime.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        result = 31 * result + (abs != null ? abs.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        return result;
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

    public Timestamp getCrawletime() {
        return crawletime;
    }

    public void setCrawletime(Timestamp crawletime) {
        this.crawletime = crawletime;
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
