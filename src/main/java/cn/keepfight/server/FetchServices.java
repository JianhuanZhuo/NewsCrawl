package cn.keepfight.server;

import cn.keepfight.utils.function.ConsumerCheck;

public abstract class FetchServices <T>{

    enum SUPPORT_METHODS{
        POST, GET
    }

    public static void fetch(String url, ConsumerCheck check){
    }
}
