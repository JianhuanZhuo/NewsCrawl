package cn.keepfight.server;

import cn.keepfight.server.Exception.StatusNoMatchException;
import org.apache.http.client.ClientProtocolException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * 基于请求判定的 Http 动作
 * Created by tom on 2017/9/8.
 */
public abstract class RequestHttpAction extends PostAction<Boolean> {

    private static Properties mp = ConfigServer.getInstance().getMatchPS();

    /**
     * 状态匹配列表提供器
     */
    abstract List<StateMatch> getStateMatchs();

    @Override
    Boolean process(BufferedReader br) throws IOException{

        StringBuilder content = new StringBuilder();
        String x;
        while ((x = br.readLine()) != null) {
//            System.out.println(x);
            content.append(x).append("\n");
            for (StateMatch m : getStateMatchs()){
                if (x.contains(mp.getProperty(m.matchPropertyKey))) {
                    if (m.isOk){
                        // 完成 OK
                        return true;
                    }
                    try {
                        throw  m.clazz.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println(content);
        // 这个状态无效，未匹配任何的预设状态
        throw new StatusNoMatchException();
    }

    static class StateMatch{
        /**
         * 属性匹配键
         */
        private String matchPropertyKey;

        private Class<? extends ClientProtocolException> clazz;

        private Boolean isOk;

        public StateMatch(Boolean isOk, String matchPropertyKey, Class<? extends ClientProtocolException> clazz) {
            this.isOk = isOk;
            this.matchPropertyKey = matchPropertyKey;
            this.clazz = clazz;
        }
    }
}
