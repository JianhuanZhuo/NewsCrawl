package cn.keepfight.server;

import cn.keepfight.server.Exception.SuccessException;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 登录动作
 * Created by tom on 2017/9/8.
 */
public class Identity {

    private Properties ip = ConfigServer.getInstance().getInfoPS();

    public String routin() throws Exception {
        String identityResult = new PatternHttpAction(){
            @Override
            String getURL() {return ip.getProperty("url.identity");}

            @Override
            String getPatternStr() {
                return "http:\\/\\/jwxk.ucas.ac.cn\\/login\\?Identity=([0-9A-Za-z\\-]+)";
            }
        }.apply();

        // 管理系统登录
        new RequestHttpAction(){
            @Override
            String getURL() {
                return ip.getProperty("url.identity.login")+identityResult;
            }

            @Override
            List<StateMatch> getStateMatchs() {
                return Collections.singletonList(new StateMatch(true, "identity.success", SuccessException.class));
            }
        }.apply();

        // 获取课程管理 ID
        return new PatternHttpAction(){

            @Override
            String getURL() {
                return ip.getProperty("url.course.main");
            }

            @Override
            String getPatternStr() {
                return "\\/courseManage\\/selectCourse\\?s=([0-9A-Za-z\\-]+)";
            }
        }.apply();
    }
}
