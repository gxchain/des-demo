package com.gxb.block.des.demo.controller.datasource;

import com.alibaba.fastjson.JSONObject;
import com.gxb.sdk.des.client.DatasourceClient;
import com.gxb.sdk.des.model.dto.ResponseObject;
import com.gxb.sdk.des.model.param.DataRequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 数据源接入示例
 *
 * @author liruobin
 * @since 2018/4/11 下午12:06
 */
@RestController
public class DataSourceClientController {
    private static final Logger log = LoggerFactory.getLogger(DataSourceClientController.class);
    DatasourceClient client;

    @PostConstruct
    public void init() {
        //1、初始化，启动心跳线程
        //创建client，入参为私钥、主链的账号、数据查询接口url
        client = new DatasourceClient("5JiZPuQbM5nHa4oX5VK7AemEv393jjnPLm...", "1.2.694723", "http://127.0.0.1/query");
        log.info("DatasourceClient初始化完成");
        //启动心跳定时线程
        ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
        es.scheduleAtFixedRate(() -> {
            try {
                //入参为产品id的list
                client.heartbeat(Arrays.asList(1));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }, 5, 30, TimeUnit.SECONDS);
        log.info("启动定时心跳线程");

    }

    //对外暴露的查询接口
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Object queryData(@RequestBody DataRequestParam requestParam) {
        //对商户参数 解密
        log.debug("解密参数");
        JSONObject param = client.decrypt(requestParam);
        //根据商户的请求参数查询数据
        log.debug("查询数据");
        ResponseObject data = queryData(param);
        //对查询结果加密
        log.debug("加密查询的数据");
        return client.encrypt(data, requestParam);
    }

    /**
     * @param param
     * @return
     */
    private ResponseObject queryData(JSONObject param) {
        //数据源实现自己的查询逻辑,组织反馈结果
        Object data = null;
        return ResponseObject.builder().data(data).success(true).build();
    }
}
