package com.gxb.block.des.demo.controller.datasource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gxb.block.des.demo.util.HttpUtil;
import com.gxb.sdk.des.model.param.DataRequestParam;
import com.gxchain.common.signature.MsgCryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liruobin
 * @since 2018/8/7 下午8:25
 */
@Slf4j
public class DataSourceQueryTest {
    public static void main(String[] args) {
        //数据源查询url
        String url = "";
        //数据产品参数
        JSONObject param = new JSONObject();
        String idcard = "445121199003294599";
        param.put("idcard_md5", DigestUtils.md5Hex(idcard));

        ////////////////////////////////////////
        JSONObject params = new JSONObject();
        params.put("timestamp", System.currentTimeMillis());
        params.put("params", param);
        long nonce = RandomUtils.nextLong(0L, Long.MAX_VALUE);
        //商户公私钥
        String privateKey = "5K8iH1jMJxn8TKXXgHJHjkf8zGXsbVPv.......";
        //商户公钥
        String publicKey = "GXC4ywUcU8h6zPqESvAMkGREmmg9r54...........";
        //数据源公钥
        String datasourcePublicKey = publicKey;

        DataRequestParam requestParam = DataRequestParam.builder().productId(10).params(MsgCryptUtil.encrypt(privateKey, datasourcePublicKey, nonce, params.toJSONString())).
                publicKey(publicKey).nonce(nonce).merchantAccountId("1.2.323232").build();
        log.info(JSON.toJSONString(param));
        String result = HttpUtil.sendPost(url, JSON.toJSONString(requestParam));
        log.info("result:{}", result);
        JSONObject resultJson = JSON.parseObject(result);
        String data = resultJson.getString("data");
        if (StringUtils.isNotBlank(data)) {
            log.info("decrypt:" + MsgCryptUtil.decrypt(privateKey, datasourcePublicKey, nonce, data));
        }
        String check = idcard.substring(idcard.length()-6);
        log.info(check+":"+DigestUtils.md5Hex(check));
    }
}
