package cn.keepfight.newsCrawl;

/**
 * Created by 卓建欢 on 2017/11/27.
 */
public class IPProxyDao {
    private String ip;
    private Integer port;
    private String location;
    private String mode;
    private String type;
    private String alive;
    private String passTime;

    public IPProxyDao() {
    }

    public IPProxyDao(String line) {
        try {
            String[] ss = line.split(" ");
            ip = ss[0];
            port = Integer.valueOf(ss[1]);
            location = ss[2];
            mode = ss[3];
            type = ss[4];
            alive = ss[5];
            passTime = ss[6] + " " + ss[7];
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlive() {
        return alive;
    }

    public void setAlive(String alive) {
        this.alive = alive;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    @Override
    public String toString() {
        return "IPProxyDao{" +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", location='" + location + '\'' +
                ", mode='" + mode + '\'' +
                ", type='" + type + '\'' +
                ", alive='" + alive + '\'' +
                ", passTime='" + passTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IPProxyDao that = (IPProxyDao) o;

        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (mode != null ? !mode.equals(that.mode) : that.mode != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (alive != null ? !alive.equals(that.alive) : that.alive != null) return false;
        return passTime != null ? passTime.equals(that.passTime) : that.passTime == null;
    }

    @Override
    public int hashCode() {
        int result = (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (alive != null ? alive.hashCode() : 0);
        result = 31 * result + (passTime != null ? passTime.hashCode() : 0);
        return result;
    }

    public boolean notNull(){
        return !(ip==null);
    }
}
