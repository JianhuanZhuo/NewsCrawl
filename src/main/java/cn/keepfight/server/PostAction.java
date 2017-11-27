package cn.keepfight.server;

import cn.keepfight.server.Exception.StatusCodeException;
import cn.keepfight.utils.FXUtils;
import cn.keepfight.utils.lang.Pair;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * http 相关动作
 * Created by tom on 2017/9/8.
 */
public abstract class PostAction<T> {

    abstract String getURL();

    /**
     * 请求参数列表，默认不带参数
     */
    List<Pair<String, String>> getParams() {
        return new ArrayList<>(1);
    }

    /**
     * 返回内容处理
     *
     * @param br 需要处理的内容，不需要关闭
     * @return 处理后的结果
     */
    abstract T process(BufferedReader br) throws IOException;

    private ResponseHandler<T> resultConvert() {
        return httpResponse -> {
            HttpEntity entity = httpResponse.getEntity();
            if (httpResponse.getStatusLine().getStatusCode() < 300 && entity != null) {
                Charset charset = ContentType.getOrDefault(entity).getCharset();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset))) {
                    return process(br);
                }
            }else{
                throw new StatusCodeException();
            }
        };
    }

    public T apply() throws Exception {
        return HttpConnectServer.getInstance().applyHttpclient((httpclient) -> {
            HttpPost post = new HttpPost(getURL());
            List<NameValuePair> formparams = getParams().stream()
                    .map(x -> new BasicNameValuePair(x.getKey(), x.getValue()))
                    .collect(Collectors.toList());
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            post.setEntity(entity);
            System.out.println("请求时间: "+FXUtils.stampToDateTime(System.currentTimeMillis()));
            System.out.println("请求地址: "+post.getRequestLine());
            System.out.println("请求参数: "+formparams);
            return httpclient.execute(post, resultConvert());
        });
    }
}
