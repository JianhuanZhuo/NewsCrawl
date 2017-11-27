package cn.keepfight.server;

import org.dom4j.Document;
import org.dom4j.Node;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 登录动作
 * Created by tom on 2017/9/8.
 */
public class SelectedAction extends TableHttpAction<List<SelectedAction.SelectedCourse>>{

    private static Properties ip = ConfigServer.getInstance().getInfoPS();

    @Override
    String getURL() {
        return ip.getProperty("url.course.selected");
    }

    @Override
    List<SelectedCourse> parseTable(Document document) {
        List<Node> trs = document.selectNodes("/table/tbody/tr");
        return trs.stream()
                .map(node->{
                    List<Node> tds = node.selectNodes("*");
                    String serial = tds.get(1).getStringValue().trim();
                    String name = tds.get(2).getStringValue().trim();
                    boolean isXWK = tds.get(4).getStringValue().trim().equals("是");
                    String period = tds.get(5).getStringValue().trim();
                    return new SelectedCourse(isXWK, serial, period, name);
                })
                .collect(Collectors.toList());
    }

    public static class SelectedCourse{
        /**
         * 是否为学位课
         */
        private boolean isXWK;

        /**
         * 课程代码
         */
        private String courseSerial;

        /**
         * 上课日期
         */
        private String name;

        /**
         * 上课日期
         */
        private String period;

        public SelectedCourse(boolean isXWK, String courseSerial, String period, String name) {
            this.isXWK = isXWK;
            this.courseSerial = courseSerial;
            this.period = period;
            this.name=name;
        }

        public boolean isXWK() {
            return isXWK;
        }

        public void setXWK(boolean XWK) {
            isXWK = XWK;
        }

        public String getCourseSerial() {
            return courseSerial;
        }

        public void setCourseSerial(String courseSerial) {
            this.courseSerial = courseSerial;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.courseSerial+"-"+this.name+" "+(this.isXWK?"学位课":"非学位课")+" "+this.period;
        }
    }
}
