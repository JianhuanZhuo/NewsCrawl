package cn.keepfight.lucene;

public class QueryFormDao {
    private int pageCapacity = 10;
    private int currentPage = 1;
    private String content = "*";
    private String method = "kw";
    private String time = "year";
    private String sort = "relative";
    private String match = "all";
    private String duplicate = "off";

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(String duplicate) {
        this.duplicate = duplicate;
    }

    @Override
    public String toString() {
        return "QueryFormDao{" +
                "pageCapacity=" + pageCapacity +
                ", currentPage=" + currentPage +
                ", content='" + content + '\'' +
                ", method='" + method + '\'' +
                ", time='" + time + '\'' +
                ", sort='" + sort + '\'' +
                ", match='" + match + '\'' +
                ", duplicate='" + duplicate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryFormDao that = (QueryFormDao) o;

        if (pageCapacity != that.pageCapacity) return false;
        if (currentPage != that.currentPage) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        if (match != null ? !match.equals(that.match) : that.match != null) return false;
        return duplicate != null ? duplicate.equals(that.duplicate) : that.duplicate == null;
    }

    @Override
    public int hashCode() {
        int result = pageCapacity;
        result = 31 * result + currentPage;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (match != null ? match.hashCode() : 0);
        result = 31 * result + (duplicate != null ? duplicate.hashCode() : 0);
        return result;
    }

    public String getQueryWithoutPage() {
        return "pageCapacity=" + pageCapacity +
                "&content=" + content +
                "&method=" + method +
                "&time=" + time +
                "&sort=" + sort +
                "&match=" + match +
                "&duplicate=" + duplicate;
    }
}
