package cn.keepfight.server;

import cn.keepfight.utils.function.FunctionCheckIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于模式获取的 Http 动作
 * Created by tom on 2017/9/8.
 */
public class ContentHttpAction {

    public FunctionCheckIO<InputStreamReader, String> getProcessor() throws IOException{
        return inputStreamReader -> {
            BufferedReader br = new BufferedReader(inputStreamReader);
            // 获得全部内容
            StringBuilder content = new StringBuilder();
            String x;
            while ((x = br.readLine()) != null) {
                content.append(x).append("\n");
            }
            return content.toString();
        };
    }
}
