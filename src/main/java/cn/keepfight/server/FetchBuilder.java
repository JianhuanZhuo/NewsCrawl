package cn.keepfight.server;

import cn.keepfight.utils.FXUtils;
import cn.keepfight.utils.function.FunctionCheckIO;
import cn.keepfight.utils.lang.Pair;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FetchBuilder<K> {
    private String url = "";
    private List<Pair<String, String>> params = new ArrayList<>(1);
    private FetchServices.SUPPORT_METHODS method = FetchServices.SUPPORT_METHODS.GET;
    private FunctionCheckIO<InputStreamReader, K> processor = null;


    public String getUrl() {
        return url;
    }

    public FetchBuilder<K> setUrl(String url) {
        this.url = url;
        return this;
    }

    public FetchBuilder<K> setParams(List<Pair<String, String>> params) {
        this.params = params;
        return this;
    }

    public FetchBuilder<K> setMethod(FetchServices.SUPPORT_METHODS method) {
        this.method = method;
        return this;
    }

    public FetchBuilder<K> setProcessor(FunctionCheckIO<InputStreamReader, K> processor) {
        this.processor = processor;
        return this;
    }

    public List<Pair<String, String>> getParams() {
        return params;
    }

    public FetchServices.SUPPORT_METHODS getMethod() {
        return method;
    }

    public FunctionCheckIO<InputStreamReader, K> getProcessor() {
        return processor;
    }


    public K fetch(CloseableHttpClient httpclient) throws Exception {

        HttpUriRequest methodAction;

        if (httpclient==null){
            throw new Exception("httpclient not allow null");
        }

        switch (getMethod()) {
            case POST:
                HttpPost post = new HttpPost(getUrl());
                post.setEntity(new UrlEncodedFormEntity(getParams().stream()
                        .map(x -> new BasicNameValuePair(x.getKey(), x.getValue()))
                        .collect(Collectors.toList()), Consts.UTF_8));
                methodAction = post;
                break;
            case GET:
                methodAction = new HttpGet(getUrl() + getParams().stream().map(x -> x.getKey() + "=" + x.getValue())
                        .collect(Collectors.joining("&", "?", "")));
                break;
            default:
                throw new Exception("not support method : " + getMethod());
        }

        System.out.println("请求方法: " + getMethod());
        System.out.println("请求时间: " + FXUtils.stampToDateTime(System.currentTimeMillis()));
        System.out.println("请求地址: " + methodAction.getRequestLine());
        System.out.println("请求参数: " + getParams());

        methodAction.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        methodAction.setHeader("Host", "www.xicidaili.com");

        return httpclient.execute(
                methodAction, httpResponse -> {
                    HttpEntity entity = httpResponse.getEntity();
                    if (httpResponse.getStatusLine().getStatusCode() < 300 && entity != null) {
                        Charset charset = ContentType.getOrDefault(entity).getCharset();
                        try (InputStreamReader br = new InputStreamReader(entity.getContent(), charset)) {
                            return getProcessor().apply(br);
                        }
                    } else {
                        System.out.println("httpResponse.getStatusLine().getStatusCode():"+httpResponse.getStatusLine().getStatusCode());
                        System.out.println("entity : "+entity);
                        Charset charset = ContentType.getOrDefault(entity).getCharset();
                        try (InputStreamReader br = new InputStreamReader(entity.getContent(), charset)) {
                            BufferedReader buf = new BufferedReader(br);
                            buf.lines().forEach(System.out::println);
                        }
                        throw new IOException();
                    }
                });
    }
}
