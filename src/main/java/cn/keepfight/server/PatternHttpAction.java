package cn.keepfight.server;

import cn.keepfight.server.Exception.PatternNoExistException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于模式获取的 Http 动作
 * Created by tom on 2017/9/8.
 */
public abstract class PatternHttpAction extends PostAction<String> {

    private Pattern pattern = Pattern.compile(getPatternStr());

    /**
     * 获得模式匹配表达式字符串
     */
    abstract String getPatternStr();

    @Override
    String process(BufferedReader br) throws IOException{
        // 获得全部内容
        StringBuilder content = new StringBuilder();
        String x;
        while ((x = br.readLine()) != null) {
            content.append(x).append("\n");
        }

        // 匹配
        Matcher m = pattern.matcher(content.toString());
        if (m.find()) {
            return m.group(1);
        }
        throw new PatternNoExistException();
    }
}
