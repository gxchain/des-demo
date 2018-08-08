package com.gxb.block.des.demo.controller.merchant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gxb.sdk.des.client.MerchantClient;
import com.gxb.sdk.des.exception.GxbApiException;
import com.gxb.sdk.des.model.dto.DataExchangeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liruobin
 * @since 2018/4/12 下午2:23
 */
public class MerchantClientDemo {
    private static final Logger log = LoggerFactory.getLogger(MerchantClientDemo.class);
    static MerchantClient client;

    public static void init() {
        //1、初始化
        //创建client，入参为私钥、主链的账号
        client = new MerchantClient("5KHoUrUTTwAyJfJPLbfmw8KK3DbEGq2Rt............", "1.2.694723");
        log.info("MerchantClient初始化完成");
    }

    public static JSONObject doParam(){
        //商户自己组织参数
        return new JSONObject();
    }

    public static void main(String[] args) {
        //初始化--初始化执行一次就够了
        init();
        long l1 = System.currentTimeMillis();
        try {
            JSONObject param = doParam();
            //创建交易
            String requestId = client.createDataExchangeRequest(2, param);
            //获取结果
            DataExchangeDto result = client.getResult(requestId);
            log.info(JSON.toJSONString(result));
        } catch (GxbApiException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            long l2 = System.currentTimeMillis();
            log.info("总耗时：" + (l2 - l1) + "ms");
        }
    }
}
