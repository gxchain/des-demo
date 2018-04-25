package com.gxb.block.des.demo.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liruobin
 * @since 2018/4/11 下午2:51
 */
public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static OkHttpClient client = new OkHttpClient();
    public static String sendPost(String url, String paramJson) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        RequestBody body = RequestBody.create(JSON, paramJson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();//得到Response 对象
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                log.error("请求失败：code:{},message:{}", response.code(), response.message());
            }
        } catch (Exception e) {
            log.error("http请求发生异常：" + e.getMessage(), e);
        }
        return null;
    }
}
