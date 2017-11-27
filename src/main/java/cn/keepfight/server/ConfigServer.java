package cn.keepfight.server;

import cn.keepfight.utils.ConfigUtil;

import java.util.Properties;

/**
 * 配置服务
 * Created by tom on 2017/9/8.
 */
public class ConfigServer {
    private static ConfigServer instance = new ConfigServer();
    public static ConfigServer getInstance() {
        return instance;
    }

    private Properties matchPS;
    private Properties infoPS;


    private ConfigServer(){
        matchPS = ConfigUtil.load("match.properties");
        infoPS = ConfigUtil.load("info.properties");
    }

    public Properties getMatchPS() {
        return matchPS;
    }

    public Properties getInfoPS() {
        return infoPS;
    }
}
