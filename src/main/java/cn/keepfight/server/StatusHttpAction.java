package cn.keepfight.server;

import cn.keepfight.utils.function.FunctionCheckIO;
import org.apache.http.client.ClientProtocolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于模式获取的 Http 动作
 * Created by tom on 2017/9/8.
 */
public class StatusHttpAction {

    private List<StateMatch> stateMatches;
    public StatusHttpAction(List<StateMatch> stateMatches){
        this.stateMatches = stateMatches;
    }

    FunctionCheckIO<InputStreamReader, Object> getProcessor() throws IOException{
        return inputStreamReader -> {
            BufferedReader br = new BufferedReader(inputStreamReader);
            // 获得全部内容
            StringBuilder content = new StringBuilder();
            String x;
            while ((x = br.readLine()) != null) {
                content.append(x).append("\n");
            }
            for (StateMatch m : stateMatches){
                if (content.toString().contains(m.key)) {
                    return m.value;
                }
            }
            return null;
        };
    }


    static class StateMatch{
        /**
         * 属性匹配键
         */
        private String key;

        private Object value;

        public StateMatch(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
