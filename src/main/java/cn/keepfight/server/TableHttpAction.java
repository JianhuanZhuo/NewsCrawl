package cn.keepfight.server;

import cn.keepfight.server.Exception.TableNoExistException;
import cn.keepfight.server.Exception.TableParseException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表格解析请求动作
 * Created by tom on 2017/9/8.
 */
public abstract class TableHttpAction<T> extends PostAction<T> {

    private static Pattern tablePattern = Pattern.compile("(<table[\\w\\W]+<\\/table>)");

    /**
     * 表格对象处理
     * @param document 表格对象
     */
    abstract T parseTable(Document document);

    /**
     * 表格文本预处理
     */
    String preprocess(String table){
        return table;
    }

    @Override
    T process(BufferedReader br) throws IOException {
        String x;
        StringBuilder contentBuilder = new StringBuilder();
        while ((x = br.readLine()) != null) {
            contentBuilder.append(x).append("\n");
        }
        String content = contentBuilder.toString();

        // 解析页面的已选课程信息
        Matcher m = tablePattern.matcher(content);
        if (m.find()) {
            try {
                Document document = DocumentHelper.parseText(m.group(1));
                return parseTable(document);
            } catch (DocumentException e) {
                throw new TableParseException();
            }
        }else{
            throw new TableNoExistException();
        }
    }
}
