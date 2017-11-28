package cn.keepfight.utils;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * FX 工具集合类
 * Created by tom on 2017/6/6.
 */
public class FXUtils {

    public static <T, R> R getMapper(SqlSessionFactory sqlSessionFactory, Class<T> clazz, ConsumerTR<T, R> consumer) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return consumer.accept(session.getMapper(clazz));
        }
    }

    public static <T, P> void getMapper(SqlSessionFactory sqlSessionFactory, Class<T> clazz, ConsumerT<T, P> consumer, P p) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            consumer.accept(session.getMapper(clazz), p);
        }
    }

    public static <T, R, P> R getMapper(SqlSessionFactory sqlSessionFactory, Class<T> clazz, ConsumerTRP<T, R, P> consumer, P p) throws Exception {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            return consumer.accept(session.getMapper(clazz), p);
        }
    }

    @FunctionalInterface
    public interface ConsumerTR<T, R> {
        R accept(T t) throws Exception;
    }

    @FunctionalInterface
    public interface ConsumerT<T, P> {
        void accept(T t, P p) throws Exception;
    }

    @FunctionalInterface
    public interface ConsumerTRP<T, R, P> {
        R accept(T t, P p) throws Exception;
    }


    public static String getContent(InputStreamReader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        // 获得全部内容
        StringBuilder content = new StringBuilder();
        String x;
        while ((x = br.readLine()) != null) {
            content.append(x).append("\n");
        }
        return content.toString();
    }
//    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
//    private static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 转换时间戳为时期字符串
     */
    public static String stampToDate(Long stamp, String pattern) {
        if (stamp == null) {
            return "";
        }
        return stampToLocalDate(stamp).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 转换时间戳为时期字符串
     */
    public static String stampToDate(Long stamp) {
        return stampToDate(stamp, "yyyy-MM-dd");
    }

    /**
     * 转换时间戳为日期时间字符串
     */
    public static String stampToDateTime(Long stamp) {
        if (stamp == null) {
            return "";
        }
        return stampToLocalDateTime(stamp).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 转时间戳为本地时间对象
     */
    public static LocalDate stampToLocalDate(Long stamp) {
        if (stamp == null) {
            stamp = System.currentTimeMillis();
        }
        return new Timestamp(stamp).toLocalDateTime().toLocalDate();
    }

    /**
     * 转时间戳为本地时间对象
     */
    public static LocalDate stampToLocalDate() {
        return stampToLocalDate(null);
    }

    public static long getYearNow(){
        return stampToLocalDate().getYear();
    }

    public static long getMonthNow(){
        return stampToLocalDate().getMonthValue();
    }

    /**
     * 转时间戳为本地时间对象
     */
    public static LocalDateTime stampToLocalDateTime(Long stamp) {
        if (stamp == null) {
            stamp = System.currentTimeMillis();
        }
        return new Timestamp(stamp).toLocalDateTime();
    }

    /**
     * 带默认值的分数字符串转换，若转换错误则返回默认值
     *
     * @param str          需要转换的分数
     * @param defaultValue 默认值
     */
    public static BigDecimal getDecimal(String str, BigDecimal defaultValue) {
        try {
            return new BigDecimal(str.trim().replaceAll(",", "")).stripTrailingZeros();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String deciToMoney(BigDecimal b) {
        DecimalFormat nf = new DecimalFormat("#,##0.###");
        String s = nf.format(b);
        int i = s.indexOf(".");
        if (i == -1) {
            if (!s.equals("0")) {
                s += ".00";
            }
        } else if (i == (s.length() - 2)) {
            s += "0";
        }
        return s;
    }

    /**
     * 带默认值的分数字符串转换，若转换错误则返回默认值
     * 重载方法默认值为0
     *
     * @param str 需要转换的分数
     */
    public static BigDecimal getDecimal(String str) {
        return getDecimal(str, new BigDecimal(0));
    }

    /**
     * 带默认值的长整形数字符串转换，若转换错误则返回默认值
     *
     * @param str          需要转换的长整形字符串
     * @param defaultValue 默认值
     */
    public static long getLong(String str, long defaultValue) {
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 带默认值的长整形数字符串转换，若转换错误则返回默认值
     * 重载方法，默认为0
     *
     * @param str 需要转换的长整形字符串
     */
    public static long getLong(String str) {
        return getLong(str, 0);
    }


    public static LocalDate getDate(String pattern, String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern, Locale.CHINESE));
    }

    public static String decimalStr(BigDecimal d) {
        if (d == null) {
            return "0";
        } else {
            return d.stripTrailingZeros().toPlainString();
        }
    }

    public static String decimalRateStr(BigDecimal d) {
        if (d == null) {
            return "0%";
        } else {
            return d.movePointRight(2).stripTrailingZeros().toPlainString() + "%";
        }
    }

    /**
     * 将指定路径的本地图片进行另存为，注意是本地图片，其中图片名并不对应上
     *
     * @param picUrl 图片的路径，如 D:/pic/xx.png
     * @return 包装后的图片URL，也就是 file:D:/xx/xx.png，或者 http:/xxx/xx/xx.png
     */
    public static String copyAndTransform(String picUrl) throws IOException {
        Path source = Paths.get(picUrl);
        String genFileName = generateName(source.getFileName().toString());
        File targetFile = new File("./pic/" + genFileName).getCanonicalFile();
        while (targetFile.exists()) {
            genFileName = generateName(genFileName);
            targetFile = new File("./pic/" + genFileName).getCanonicalFile();
        }
        Path target = targetFile.toPath();
        Files.copy(source, target);
        // 添加 file: 本地文件连接
        return "file:" + targetFile.getAbsolutePath();
    }

    /**
     * 随机修改文件名
     */
    public static String generateName(String source) {
        return "gen" + (source + System.currentTimeMillis()).hashCode() + source.substring(source.lastIndexOf('.'));
    }

    public static int getNumAt(BigDecimal decimal, int place, boolean left) {
        if (decimal == null) {
            throw new RuntimeException("decimal is null!");
        }
        String ds = decimal.toString();
        int p = ds.indexOf(".");
        int kp = left ? p - place : p + place;
        try {
            return Integer.valueOf("" + ds.charAt(kp));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 对字符串进行包含了对 s 字符串的空处理，转化的异常处理，去除无效切分值
     *
     * @param s       需要被切分的字符串
     * @param regex   切分的正则式
     * @param convert 转换函数
     * @return 切分后的列表
     */
    public static <T> List<T> split(String s, String regex, Function<String, T> convert) {
//        if (s==null || s.trim().equals("")){
//            return new ArrayList<>(0);
//        }

        try {
            return Arrays.stream(s.split(regex))
                    .map(x -> {
                        try {
                            return convert.apply(x);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>(0);
        }
    }

}
