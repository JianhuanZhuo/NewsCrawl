package cn.keepfight.dao;

import java.sql.Timestamp;

/**
 * Created by 卓建欢 on 2017/11/27.
 */
public class IPProxyDao {
    private Integer id;
    private String ip;
    private Integer port;
    private Timestamp lastTime = new Timestamp(System.currentTimeMillis());
    private Integer fails = 0;

    public IPProxyDao() {
    }

    public IPProxyDao(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public IPProxyDao(String line) {
        try {
            String[] ss = line.split(" ");
            ip = ss[0];
            port = Integer.valueOf(ss[1]);
        } catch (Exception e) {
            ip = null;
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
    }

    public Integer getFails() {
        return fails;
    }

    public void setFails(Integer fails) {
        this.fails = fails;
    }

    @Override
    public String toString() {
        return "IPProxyDao{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", lastTime=" + lastTime +
                ", fails=" + fails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IPProxyDao that = (IPProxyDao) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;
        if (lastTime != null ? !lastTime.equals(that.lastTime) : that.lastTime != null) return false;
        return fails != null ? fails.equals(that.fails) : that.fails == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (lastTime != null ? lastTime.hashCode() : 0);
        result = 31 * result + (fails != null ? fails.hashCode() : 0);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean notNull() {
        return !(ip == null);
    }
}
